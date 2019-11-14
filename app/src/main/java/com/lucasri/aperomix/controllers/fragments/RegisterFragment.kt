package com.lucasri.aperomix.controllers.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.Constraints
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.api.UserHelper
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.utils.toast
import kotlinx.android.synthetic.main.fragment_register.*
import android.widget.DatePicker
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*
import java.util.concurrent.TimeUnit


class RegisterFragment : UtilsAccountBasefragment() {

    private var passwordGood: Boolean = false
    private var confirmPasswordGood: Boolean = false

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        this.displayRegisterButtonIfInputNotNull()
        fragment_register_loading.visibility = View.GONE

        fragment_register_next_btn.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(fragment_register_email_edt.text).matches()){
                if (userIsAdult(fragment_register_datePicker) && fragment_register_confirmadult_checkBox.isChecked){
                    fragment_register_loading.visibility = View.VISIBLE
                    activity!!.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                    createUserInFirebaseAuthentifiaction(fragment_register_email_edt.text.toString(), fragment_register_password_edt.text.toString())

                } else context!!.toast(getString(R.string.fragment_register_mustadult_toats))


            } else context!!.toast(getString(R.string.fragment_register_register_email_toats))
        }

        fragment_register_email_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                displayRegisterButtonIfInputNotNull()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        fragment_register_pseudo_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                displayRegisterButtonIfInputNotNull()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        fragment_register_password_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                displayPasswordEditText(fragment_register_password_edt.text.toString())
                displayRegisterButtonIfInputNotNull()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        fragment_register_confirmpassword_edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                displayConfirmPasswordEditText(fragment_register_confirmpassword_edt.text.toString())
                displayRegisterButtonIfInputNotNull()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    // --------------------
    // FIREBASE
    // --------------------

    private fun createUserInFirebaseAuthentifiaction(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        Log.d(Constraints.TAG, "createUserWithEmail:success")
                        createUserInFirestore()
                    } else {
                        Log.w(Constraints.TAG, "createUserWithEmail:failure", task.exception)
                        context!!.toast(getString(R.string.fragment_register_register_faield))
                    }
                }
    }

    private fun createUserInFirestore() {
        UserHelper.getUsersCollection().document(this.getCurrentUser()!!.uid)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document!!.exists()) {
                            context!!.toast("le compte existe déja")
                        } else {
                            if (this.getCurrentUser() != null) {

                                val uid = this.getCurrentUser()!!.uid
                                val username = fragment_register_pseudo_edt.text.toString()
                                val email = fragment_register_email_edt.text.toString()
                                val birthday = "${fragment_register_datePicker.dayOfMonth}/${fragment_register_datePicker.month +1}/${fragment_register_datePicker.year}"

                                UserHelper.createUser(uid, username, email, birthday).addOnFailureListener(this.onFailureListener())
                                fragment_register_loading.visibility = View.GONE
                                context!!.toast(getString(R.string.fragment_register_register_success))
                            }
                        }

                    } else {
                        context!!.toast("get failed with " + task.exception)
                    }
                }
    }

    // --------------------
    // UTILS
    // --------------------

    fun userIsAdult(view: DatePicker): Boolean {
        val userAge = GregorianCalendar(view.year, view.month, view.dayOfMonth)
        val minAdultAge = GregorianCalendar()
        minAdultAge.add(Calendar.YEAR, -18)
        return !minAdultAge.before(userAge)
    }

    private fun displayPasswordInfo(enable: Boolean){
        val params = fragment_register_info_password.layoutParams as RelativeLayout.LayoutParams
        if (enable) params.height = RelativeLayout.LayoutParams.WRAP_CONTENT else params.height = 0
        fragment_register_info_password.layoutParams = params
    }

    private fun displayRegisterButtonIfInputNotNull(){
        if (fragment_register_email_edt.text.toString() != "" && fragment_register_pseudo_edt.text.toString() != "" &&
                fragment_register_email_edt.text.toString() != "" && passwordGood && confirmPasswordGood) displayRegisterButton(true) else displayRegisterButton(false)
    }

    private fun displayRegisterButton(enable: Boolean){
        if (enable){
            fragment_register_next_btn.isEnabled = true
            fragment_register_next_btn.alpha = 1f
        } else {
            fragment_register_next_btn.isEnabled = false
            fragment_register_next_btn.alpha = 0.3f
        }
    }

    private fun displayConfirmPasswordEditText(password: String){
        var editTextBackground: Int

        if (passwordIsGood(password) && password == fragment_register_password_edt.text.toString()){
            editTextBackground = R.drawable.background_edt_green
            confirmPasswordGood = true
        } else {
            editTextBackground = R.drawable.background_edt_red
            confirmPasswordGood = false
        }
        fragment_register_confirmpassword_edt.setBackgroundResource(editTextBackground)
    }

    private fun displayPasswordEditText(password: String){
        var editTextBackground: Int

        if (passwordIsGood(password)){
            editTextBackground = R.drawable.background_edt_green
            displayPasswordInfo(false)
            passwordGood = true
        } else{
            editTextBackground = R.drawable.background_edt_red
            displayPasswordInfo(true)
            passwordGood = false
        }
        fragment_register_password_edt.setBackgroundResource(editTextBackground)
    }

    private fun passwordIsGood(password: String): Boolean{
        return passwordContainEightChar(password) && passwordContainUpperCase(password) && passwordContainNumber(password)
    }

    private fun passwordContainEightChar(password: String): Boolean{
        return password.length >= 8
    }

    private fun passwordContainUpperCase(password: String): Boolean {
        var result : Boolean = false

        for (i in 0 until password.length) {
            val char = password[i]
            if (char.toInt() in 90 downTo 65) result = true
        }
        return result
    }

    private fun passwordContainNumber(password: String): Boolean {
        var result : Boolean = false

        for (i in 0 until password.length) {
            val char = password[i]
            if (char.toInt() in 57 downTo 48) result = true
        }
        return result
    }

    private fun launchMainActivity() {
        val myIntent = Intent(context, MainActivity::class.java)
        startActivity(myIntent)
    }
}
