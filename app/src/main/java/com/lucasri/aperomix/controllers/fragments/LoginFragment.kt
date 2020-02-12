package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.controllers.activities.RegisterActivity
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.utils.toast
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.Executors

class LoginFragment : Fragment(){

    private lateinit var auth: FirebaseAuth
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        this.configureViewModel()

        if (MainActivity.dynamicLinkMode) dynamicLinkMode()

        fragment_login_login_btn.setOnClickListener {
            if (fragment_login_email_edt.text.toString() != "" && fragment_login_password_edt.text.toString() != ""){
                displayProgressBarLayout(true)
                singIn(fragment_login_email_edt.text.toString(), fragment_login_password_edt.text.toString())
            } else context!!.toast(getString(R.string.LoginActivity_input_empty))
        }

        fragment_login_register_btn.setOnClickListener {
            context!!.launchActivity(RegisterActivity())
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureViewModel() {
        val mViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun dynamicLinkMode(){
        MainActivity.dynamicLinkMode = false
        AlertDialog.Builder(context!!)
                .setMessage(getString(R.string.DynamicLink_message_account))
                .show()
    }

    private fun singIn(email: String?, password: String?){
        auth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful) {
                Log.d(Constraints.TAG, "signInWithEmail:success")
                context!!.toast(getString(R.string.fragmentLoginSucess))
                context!!.launchActivity(AccountActivity())
            } else {
                Log.w(Constraints.TAG, "signInWithEmail:failure", task.exception)
                displayProgressBarLayout(false)
                context!!.toast(getString(R.string.LoginActivity_login_faield))
            }
        }
    }

    private fun displayProgressBarLayout(enable: Boolean){
        if (enable) fragment_login_progressBar.visibility = View.VISIBLE else fragment_login_progressBar.visibility = View.GONE
    }
}