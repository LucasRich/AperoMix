package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.database.injection.UserViewModelFactory
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.models.User
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.view.UserViewModel
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.concurrent.Executors

class AccountFragment : Fragment(){

    lateinit var userViewModel: UserViewModel
    lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureUserViewModel()
        auth = FirebaseAuth.getInstance()

        //Init view with current user
        userViewModel.getUser(auth.uid!!).addOnSuccessListener { documentSnapshot -> initView(documentSnapshot.toObject(User::class.java)) }

        fragment_account_signout.setOnClickListener {
            auth.signOut()
            activity!!.launchActivity(AccountActivity())
        }
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private fun configureUserViewModel() {
        val userViewModelFactory = UserViewModelFactory(UserDataRepository(), Executors.newSingleThreadExecutor())
        this.userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun initView(user: User?){
        if (user != null){
            fragment_account_userName.text = user.userName
            fragment_account_email.text = user.email
            fragment_account_papinGame.text = user.papinGameCounter.toString()
            fragment_account_pmuGame.text = user.pmuGameCounter.toString()
            fragment_account_beLuckyGame.text = user.beLuckyGameCounter.toString()
            fragment_account_point.text = ((user.papinGameCounter!!+user.pmuGameCounter!!+user.beLuckyGameCounter!!)*5).toString()
        }
    }
}