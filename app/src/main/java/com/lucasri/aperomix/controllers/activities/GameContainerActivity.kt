package com.lucasri.aperomix.controllers.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.fragments.BeLuckyParamFragment
import com.lucasri.aperomix.controllers.fragments.PapinGameFragment
import com.lucasri.aperomix.controllers.fragments.PmuParamFragment
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.utils.launchFragment
import kotlinx.android.synthetic.main.activity_game_container.*

class GameContainerActivity : AppCompatActivity() {

    private var ID_PAPIN = "Papin Game"
    private var ID_PMU = "Le PMU"
    private var ID_BELUCKY = "Be lucky Game"

    companion object{
        var launchMode: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_container)
        this.configureToolbar()

        when(launchMode){
            ID_PAPIN -> launchFragment(PapinGameFragment(), activity_game_container_frame.id)
            ID_PMU -> launchFragment(PmuParamFragment(), activity_game_container_frame.id)
            ID_BELUCKY -> launchFragment(BeLuckyParamFragment(), activity_game_container_frame.id)
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureToolbar() {
        setSupportActionBar(activity_game_container_toolbar as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                    leaveGame()
                    true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() { leaveGame() }

    // ---------------------
    // UTILS
    // ---------------------

    private fun leaveGame() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.GameContainer_leave_message))
                .setPositiveButton(getString(R.string.GameContainer_leave)) { dialog, which ->
                    launchActivity(MainActivity())
                    resetPapinGame()
                }
                .setNegativeButton(getString(R.string.GameContainer_cancel)) { dialog, which -> }
                .create().show()
    }

    private fun resetPapinGame(){
        PapinGameFragment.ruleList.clear()
        PapinGameFragment.passedRuleList.clear()
        PapinGameFragment.playerCounter = 0
        PapinGameFragment.navigationPlayerCounter = 0
        PapinGameFragment.count = 0
        PapinGameFragment.navigationCount = 0
        PapinGameFragment.random = 0
        PapinGameFragment.previousRandomValue = 0
        PapinGameFragment.infoRule = null
        PapinGameFragment.previousRule = null
        PapinGameFragment.papinGame = false
    }
}
