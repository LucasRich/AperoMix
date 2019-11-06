package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.RegisterActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(){

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //INIT

        fragment_login_register_btn.setOnClickListener {
            launchRegisterActivity()
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