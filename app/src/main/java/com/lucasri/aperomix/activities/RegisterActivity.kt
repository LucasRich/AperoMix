package com.lucasri.aperomix.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lucasri.aperomix.R
import com.lucasri.aperomix.fragments.RegisterFragment
import com.lucasri.aperomix.utils.addFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.configureToolbar()

        addFragment(RegisterFragment.newInstance(), activity_register_frame.id)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureToolbar() {
        this.toolbar = findViewById<View>(R.id.activity_register_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
    }
}