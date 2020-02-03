package com.lucasri.aperomix.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.AccountFragment
import com.lucasri.aperomix.controllers.fragments.LoginFragment
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.models.User
import com.lucasri.aperomix.utils.addFragment
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.fragment_be_lucky_param.view.*
import java.util.concurrent.Executors

class AccountActivity : AppCompatActivity(){

    private var toolbar: Toolbar? = null
    private lateinit var auth: FirebaseAuth
    lateinit var userViewModel: UserViewModel

    companion object{
        var user: User? = null
        var launchMode = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        this.configureToolbar()
        this.configureViewModel()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) addFragment(AccountFragment(), activity_account_frame.id)
        else addFragment(LoginFragment(), activity_account_frame.id)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureViewModel() {
        val mViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel::class.java)
    }

    private fun configureToolbar() {
        this.toolbar = findViewById<View>(R.id.activity_account_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
        ab.title = ""
    }
}