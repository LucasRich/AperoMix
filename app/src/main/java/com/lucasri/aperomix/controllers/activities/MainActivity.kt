package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.ChatFragment
import com.lucasri.aperomix.controllers.fragments.MainFragment
import com.lucasri.aperomix.controllers.fragments.SelectGameFragment
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.utils.launchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object{
        var afterGame = false
        var dynamicLinkMode = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    if (pendingDynamicLinkData != null) {
                        dynamicLinkMode = true
                        if (auth.currentUser != null) launchFragment(ChatFragment(), activity_main_frame.id) else launchActivity(AccountActivity())
                    } else {
                        dynamicLinkMode = false
                        if (afterGame) launchFragment(SelectGameFragment(), activity_main_frame.id)
                        else launchFragment(MainFragment(), activity_main_frame.id)
                        afterGame = false
                    }
                }
                .addOnFailureListener(this) { e -> println("getDynamicLink:onFailure") }
    }

    override fun onBackPressed() {
        launchActivity(MainActivity())
    }
}
