package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.LoginFragment
import com.lucasri.aperomix.utils.addFragment
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity(){

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        this.configureToolbar()

        addFragment(LoginFragment.newInstance(), activity_account_frame.id)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureToolbar() {
        this.toolbar = findViewById<View>(R.id.activity_account_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
    }
}