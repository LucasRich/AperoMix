package com.lucasri.aperomix.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.adapter.PlayerAdapter
import com.lucasri.aperomix.model.Player

import java.util.ArrayList

import com.lucasri.aperomix.fragments.MainFragment
import com.lucasri.aperomix.utils.addFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val toolbar: Toolbar? = null
    var playerList = ArrayList<Player>()
    private var playerAdapter: PlayerAdapter? = null
    private var counter = 4

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(MainFragment.newInstance(), activity_main_frame.id)
    }

    override fun onBackPressed() {
        launchMainActivity()
        println("github test")
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(this, MainActivity::class.java)
        this.startActivity(myIntent)
    }
}
