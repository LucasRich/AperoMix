package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.AccountFragment
import com.lucasri.aperomix.controllers.fragments.LoginFragment
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.utils.launchFragment
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.activity_account.*
import java.util.concurrent.Executors

class AccountActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account)
        this.configureToolbar()
        this.configureViewModel()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) launchFragment(AccountFragment(), activity_account_frame.id)
        else launchFragment(LoginFragment(), activity_account_frame.id)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureViewModel() {
        val mViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_account_toolbar as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
    }
}