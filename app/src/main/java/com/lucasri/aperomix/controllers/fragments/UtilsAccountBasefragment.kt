package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.utils.toast


abstract class UtilsAccountBasefragment : Fragment() {

    lateinit var auth: FirebaseAuth

    // --------------------
    // UTILS
    // --------------------

    fun getCurrentUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun isCurrentUserLogged(): Boolean?{
        return this.getCurrentUser() != null
    }

    // --------------------
    // HANDLER
    // --------------------

    protected fun onFailureListener(): OnFailureListener {
        return OnFailureListener { context!!.toast(getString(R.string.basefragment_utils_account)) }
    }

    private fun launchAccountActivity() {
        val myIntent: Intent = Intent(context!!, AccountActivity::class.java)
        this.startActivity(myIntent)
    }
}

