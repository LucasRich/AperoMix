package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.RegisterFragment
import com.lucasri.aperomix.utils.launchFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.configureToolbar()

        launchFragment(RegisterFragment(), activity_register_frame.id)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureToolbar() {
        setSupportActionBar(activity_register_toolbar as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}