package com.lucasri.aperomix.controllers.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.MainFragment
import com.lucasri.aperomix.controllers.fragments.SelectGameFragment
import com.lucasri.aperomix.models.Player

import java.util.ArrayList

import com.lucasri.aperomix.utils.addFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var afterGame: Boolean = false
    }

    private val toolbar: Toolbar? = null
    var playerList = ArrayList<Player>()
    private var counter = 4

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (afterGame) {
            addFragment(SelectGameFragment(), activity_main_frame.id)
            afterGame = false
        } else addFragment(MainFragment(), activity_main_frame.id)
    }

    override fun onBackPressed() {
        launchMainActivity()
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(this, MainActivity::class.java)
        this.startActivity(myIntent)
    }
}
