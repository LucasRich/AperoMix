package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.MainFragment
import com.lucasri.aperomix.controllers.fragments.SelectGameFragment
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.utils.launchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var afterGame: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (afterGame) launchFragment(SelectGameFragment(), activity_main_frame.id)
        else launchFragment(MainFragment(), activity_main_frame.id)
        afterGame = false
    }

    override fun onBackPressed() {
        launchActivity(MainActivity())
    }
}
