package com.lucasri.aperomix.controllers.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.BeLuckyParamFragment
import com.lucasri.aperomix.controllers.fragments.PapinGameFragment
import com.lucasri.aperomix.controllers.fragments.PmuParamFragment

class GameContainer : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var bundleValue: String = ""

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_container)

        this.configureToolbar()

        val bundle = intent.extras
        if (bundle != null) bundleValue = bundle.getString("startParameter", "")

        when(bundleValue){
            "Papin Game" -> launchFragmentPapinGame()
            "Le PMU" -> launchFragmentPmuParam()
            "Be lucky Game" -> launchFragmentBeLuckyGame()
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureToolbar() {
        this.toolbar = findViewById<View>(R.id.activity_game_container_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (bundleValue != ""){
            leaveGame()
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if(bundleValue != ""){
                    leaveGame()
                    true
                } else false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun leaveGame() {
        AlertDialog.Builder(this)
                .setMessage("Êtes vous sûr de vouloir quitter la partie ?")
                .setPositiveButton("Quitter") { dialog, which -> launchMainActivity() }
                .setNegativeButton("Annuler") { dialog, which -> }
                .create().show()
    }

    private fun launchFragmentPapinGame() {
        val fragment = PapinGameFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_game_container_frame, fragment)
        transaction.commit()
    }

    private fun launchFragmentPmuParam() {
        val fragment = PmuParamFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_game_container_frame, fragment)
        transaction.commit()
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(this, MainActivity::class.java)
        this.startActivity(myIntent)
    }

    private fun launchFragmentBeLuckyGame() {
        val fragment = BeLuckyParamFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_game_container_frame, fragment)
        transaction.commit()
    }

}
