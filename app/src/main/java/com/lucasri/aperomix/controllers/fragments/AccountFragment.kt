package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.auth.User
import com.lucasri.aperomix.R
import com.lucasri.aperomix.api.UserHelper
import com.lucasri.aperomix.controllers.activities.RegisterActivity
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_login.*

class AccountFragment : UtilsAccountBasefragment(){

    companion object {
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        UserHelper.getUser(this.getCurrentUser()!!.uid).addOnSuccessListener {
            //val currentUser = documentSnapshot.toObject(User::class.java)
            //fragment_account_txt.text = currentUser.getUsername()
        }
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun launchRegisterActivity() {
        val myIntent: Intent = Intent(context, RegisterActivity::class.java)
        this.startActivity(myIntent)
    }
}