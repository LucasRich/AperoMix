package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.MainActivity
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.utils.InitGame
import com.lucasri.aperomix.utils.longToast
import com.lucasri.aperomix.utils.random
import kotlinx.android.synthetic.main.fragment_papin_game.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import java.util.ArrayList
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import com.lucasri.aperomix.view.adapter.DisplayImgViewPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.lucasri.aperomix.controllers.fragments.EndGameFragment.Companion.ID_PAPIN_MODE
import com.lucasri.aperomix.controllers.fragments.EndGameFragment.Companion.launchMode

class PapinGameFragment: Fragment(){
    private var mediaPlayer = MediaPlayer()
    private var textPop: Animation? = null
    private var boyAndGirl = true
    private var passedRuleCounter: Int = 0
    private var doubleScreen: Boolean = false

    companion object{
        val playerList = ArrayList<Player>()

        var ruleList = ArrayList<String>()
        var passedRuleList = ArrayList<String>()

        var playerCounter = 0
        var navigationRulePlayerCounter: Int = 0
        var count = 0
        var navigationRuleCount: Int = 0
        var random: Int = 0
        var previousRandomValue: Int = 0

        var infoRule: String? = null
        var previousRule = "null"

        var papinGame = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_papin_game, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textPop = loadAnimation(context!!, R.anim.item_animation_pop)
        this.initScreen()
        this.displayNavigationRule(false)

        if (count == 0){
            fragment_papin_game_rule_txt_single.text = getString(R.string.PapinBegin)
            this.askBoyAndGirl()
            this.initPlayerList()
        } else {
            displayRule(passedRuleList[passedRuleList.size - 1])
            displayCounter(count)
        }

        fragment_papin_game_container.setOnClickListener {
            if (ruleList.isEmpty()) {
                InitGame.initRuleList(ruleList, boyAndGirl)
            }

            if (count == navigationRuleCount) {
                displayNavigationRule(false)
                count++
                navigationRuleCount = count

                if (count < 53) {
                    displayCounter(count)

                    if (playerList.isNotEmpty()) {
                        displayPlayerName(playerList[playerCounter].playerName)
                        navigationRulePlayerCounter = playerCounter
                        playerCounter++

                        if (playerCounter == playerList.size) {
                            playerCounter = 0
                        }
                    }
                    play()
                } else {
                    gameFinish()
                }
                passedRuleCounter = passedRuleList.size - 1
            } else {
                context!!.longToast(getString(R.string.Papin_error1))
            }
        }

        fragment_papin_game_previous_rule_single.setOnClickListener {
            displayPreviousRule()
        }

        fragment_papin_game_next_rule_single.setOnClickListener {
            displayNextRule()
        }

        fragment_papin_game_previous_rule_doubleScreen1.setOnClickListener {
            displayPreviousRule()
        }

        fragment_papin_game_next_rule_doubleScreen1.setOnClickListener {
            displayNextRule()
        }

        fragment_papin_game_previous_rule_doubleScreen2.setOnClickListener {
            displayPreviousRule()
        }

        fragment_papin_game_next_rule_doubleScreen2.setOnClickListener {
            displayNextRule()
        }

        fragment_papin_game_info_btn_single.setOnClickListener {
            if (!papinGame) displayAlertDialog(infoRule!!) else displayPapinGameRule()
        }

        fragment_papin_game_info_btn_doubleScreen1.setOnClickListener {
            if (!papinGame) displayAlertDialog(infoRule!!) else displayPapinGameRule()
        }

        fragment_papin_game_info_btn_doubleScreen2.setOnClickListener {
            if (!papinGame) displayAlertDialog(infoRule!!) else displayPapinGameRule()
        }

        fragment_papin_game_bottom_btn_single.setOnClickListener {
            if (count > 52) launchMainActivity() else playCountDown()
        }

        fragment_papin_game_bottom_btn_doubleScreen1.setOnClickListener {
            if (count > 52) launchMainActivity() else playCountDown()
        }

        fragment_papin_game_bottom_btn_doubleScreen2.setOnClickListener {
            if (count > 52) launchMainActivity() else playCountDown()
        }

        fragment_papin_game_double_screen_btn_single.setOnClickListener {
            if (count != 0) {
                doubleScreen()
                doubleScreen = true
                initRuleInfo(ruleList[previousRandomValue])
            }
            else {
                context!!.longToast(getString(R.string.Papin_error2))
            }
        }

        fragment_papin_game_double_screen_btn_doubleScreen1.setOnClickListener {
            initScreen()
            doubleScreen = false
            initRuleInfo(ruleList[previousRandomValue])
        }

        fragment_papin_game_double_screen_btn_doubleScreen2.setOnClickListener {
            initScreen()
            doubleScreen = false
            initRuleInfo(ruleList[previousRandomValue])
        }

        fragment_papin_game_navigate_btn_single.setOnClickListener {
            if (count != 0) {
                displayNavigationRule(true)
            } else {
                context!!.longToast(getString(R.string.Papin_error2))
            }
        }

        fragment_papin_game_navigate_btn_doubleScreen1.setOnClickListener {
            if (count != 0) {
                displayNavigationRule(true)
            } else {
                context!!.longToast(getString(R.string.Papin_error2))
            }
        }

        fragment_papin_game_navigate_btn_doubleScreen2.setOnClickListener {
            if (count != 0) {
                displayNavigationRule(true)
            } else {
                context!!.longToast(getString(R.string.Papin_error2))
            }
        }

        fragment_papin_game_display_papin_game_rule_understand.setOnClickListener {
            fragment_papin_game_display_papin_game_rule_container.visibility = View.INVISIBLE
            fragment_papin_game_container.isEnabled = true
        }

        fragment_papin_game_display_papin_game_rule__viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 2) fragment_papin_game_display_papin_game_rule_understand.visibility = View.VISIBLE
                else fragment_papin_game_display_papin_game_rule_understand.visibility = View.INVISIBLE
            }
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.lucasri.aperomix.R.id.doubleScreen -> {
                if (count != 0) {
                    if (!doubleScreen) {
                        doubleScreen()
                        doubleScreen = true
                    } else {
                        initScreen()
                        doubleScreen = false
                    }
                } else {
                    context!!.longToast(getString(R.string.Papin_error2))
                }

                return true
            }
            R.id.previousRule -> {
                if (count != 0) {
                    displayNavigationRule(true)
                } else {
                    context!!.longToast(getString(R.string.Papin_error2))
                }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initPlayerList() {
        playerList.addAll(MainFragment.playerList)
    }

    private fun initScreen() {
        fragment_papin_game_name_single.visibility = View.VISIBLE
        fragment_papin_game_rule_txt_single.visibility = View.VISIBLE
        fragment_papin_game_counter_single.visibility = View.VISIBLE
        fragment_papin_game_btn_container_single.visibility = View.VISIBLE

        fragment_papin_game_bottom_btn_single.visibility = View.INVISIBLE
        fragment_papin_game_display_papin_game_rule_container.visibility = View.INVISIBLE
        fragment_papin_game_next_rule_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_next_rule_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_previous_rule_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_previous_rule_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_name_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_name_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_rule_txt_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_rule_txt_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_counter_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_counter_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_info_btn_doubleScreen1.visibility = View.GONE
        fragment_papin_game_info_btn_doubleScreen2.visibility = View.GONE
        fragment_papin_game_bottom_btn_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_bottom_btn_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_btn_container_doubleScreen1.visibility = View.INVISIBLE
        fragment_papin_game_btn_container_doubleScreen2.visibility = View.INVISIBLE
        fragment_papin_game_info_btn_single.visibility = View.INVISIBLE
        line_double_screen.visibility = View.INVISIBLE
    }

    private fun doubleScreen() {
        fragment_papin_game_name_single.visibility = View.INVISIBLE
        fragment_papin_game_rule_txt_single.visibility = View.INVISIBLE
        fragment_papin_game_counter_single.visibility = View.INVISIBLE
        fragment_papin_game_info_btn_single.visibility = View.INVISIBLE
        fragment_papin_game_bottom_btn_single.visibility = View.INVISIBLE
        fragment_papin_game_btn_container_single.visibility = View.INVISIBLE
        fragment_papin_game_next_rule_single.visibility = View.INVISIBLE
        fragment_papin_game_previous_rule_single.visibility = View.INVISIBLE

        fragment_papin_game_name_doubleScreen1.visibility = View.VISIBLE
        fragment_papin_game_name_doubleScreen2.visibility = View.VISIBLE
        fragment_papin_game_rule_txt_doubleScreen1.visibility = View.VISIBLE
        fragment_papin_game_rule_txt_doubleScreen2.visibility = View.VISIBLE
        fragment_papin_game_counter_doubleScreen1.visibility = View.VISIBLE
        fragment_papin_game_counter_doubleScreen2.visibility = View.VISIBLE
        fragment_papin_game_btn_container_doubleScreen1.visibility = View.VISIBLE
        fragment_papin_game_btn_container_doubleScreen2.visibility = View.VISIBLE
        fragment_papin_game_btn_container_doubleScreen1.visibility = View.VISIBLE
        fragment_papin_game_btn_container_doubleScreen2.visibility = View.VISIBLE
        line_double_screen.visibility = View.VISIBLE
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun newGame(){
        ruleList.clear()
        passedRuleList.clear()

        playerCounter = 0
        navigationRulePlayerCounter = 0
        count = 0
        navigationRuleCount = 0
        random = 0
        previousRandomValue = 0

        infoRule = null
        previousRule = "null"

        papinGame = false
    }

    private fun displayPapinGameRule (){
        fragment_papin_game_display_papin_game_rule_container.visibility = View.VISIBLE
        fragment_papin_game_display_papin_game_rule_understand.visibility = View.INVISIBLE
        fragment_papin_game_display_papin_game_rule__viewPager.adapter = DisplayImgViewPagerAdapter(activity!!.supportFragmentManager)
        fragment_papin_game_container.isEnabled = false
    }

    private fun displayNavigationRule(display: Boolean){
        if (!doubleScreen){
            if (display) {
                fragment_papin_game_next_rule_single.visibility = View.VISIBLE
                fragment_papin_game_previous_rule_single.visibility = View.VISIBLE
            } else{
                fragment_papin_game_next_rule_single.visibility = View.INVISIBLE
                fragment_papin_game_previous_rule_single.visibility = View.INVISIBLE
            }
        } else{
            if (display) {
                fragment_papin_game_next_rule_doubleScreen1.visibility = View.VISIBLE
                fragment_papin_game_next_rule_doubleScreen2.visibility = View.VISIBLE
                fragment_papin_game_previous_rule_doubleScreen1.visibility = View.VISIBLE
                fragment_papin_game_previous_rule_doubleScreen2.visibility = View.VISIBLE
            } else{
                fragment_papin_game_next_rule_doubleScreen1.visibility = View.INVISIBLE
                fragment_papin_game_next_rule_doubleScreen2.visibility = View.INVISIBLE
                fragment_papin_game_previous_rule_doubleScreen1.visibility = View.INVISIBLE
                fragment_papin_game_previous_rule_doubleScreen2.visibility = View.INVISIBLE
            }
        }
    }

    private fun displayRule(rule: String){
        fragment_papin_game_rule_txt_single.text = rule
        fragment_papin_game_rule_txt_doubleScreen1.text = rule
        fragment_papin_game_rule_txt_doubleScreen2.text = rule

        if (doubleScreen){
            fragment_papin_game_rule_txt_doubleScreen1.startAnimation(textPop)
            fragment_papin_game_rule_txt_doubleScreen2.startAnimation(textPop)
        } else{
            fragment_papin_game_rule_txt_single.startAnimation(textPop)
        }
    }

    private fun getRandom(){
        if (ruleList.size > 4) {
            do {
                random = random(0, ruleList.size-1)
                previousRandomValue = random
            } while (ruleList[random] == previousRule)
        } else {
            random = random(0, ruleList.size-1)
            previousRandomValue = random
        }
    }

    private fun play() {
        getRandom()
        displayRule(ruleList[random])
        passedRuleList.add(ruleList[random])
        previousRule = ruleList[random]
        initRuleInfo(ruleList[random])
        ruleList.removeAt(random)
    }

    private fun displayPreviousRule() {
        if (passedRuleCounter > 0) {
            navigationRulePlayerCounter--

            if (navigationRulePlayerCounter < 0) navigationRulePlayerCounter = playerList.size - 1

            displayPlayerName(playerList[navigationRulePlayerCounter].playerName)
            passedRuleCounter--
            displayRule(passedRuleList[passedRuleCounter])
            initRuleInfo(passedRuleList[passedRuleCounter])
            navigationRuleCount--
            displayCounter(navigationRuleCount)
        }
    }

    private fun displayNextRule() {
        if (passedRuleCounter < passedRuleList.size - 1) {
            navigationRulePlayerCounter++

            if (navigationRulePlayerCounter > playerList.size - 1) navigationRulePlayerCounter = 0

            displayPlayerName(playerList[navigationRulePlayerCounter].playerName)
            passedRuleCounter++
            displayRule(passedRuleList[passedRuleCounter])
            initRuleInfo(passedRuleList[passedRuleCounter])
            navigationRuleCount++
            displayCounter(navigationRuleCount)
        } else {
            displayNavigationRule(false)
            displayAlertDialog(getString(R.string.PapinResumeGame))
        }
    }

    private fun displayAlertDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.info_dialog, null)
        dialogBuilder.setView(dialogView)

        //INIT
        dialogView.text_info.text = message

        //DISPLAY DIALOG
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun gameFinish() {
        count = 53
        navigationRuleCount = 53

        launchMode = ID_PAPIN_MODE
        newGame()
        launchFragmentEndGame()
    }

    private fun askBoyAndGirl() {
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.PapinMixteMsg))
                .setMessage(getString(R.string.PapinMixteOrNot))
                .setPositiveButton(getString(R.string.PapinMixte)) { _, _ -> boyAndGirl = true }
                .setNegativeButton(getString(R.string.PapinNotMixte)) { _, _ -> boyAndGirl = false }
                .show()
    }

    private fun displayCounter(count: Int) {
        fragment_papin_game_counter_single.text = "$count/52"
        fragment_papin_game_counter_doubleScreen1.text = "$count/52"
        fragment_papin_game_counter_doubleScreen2.text = "$count/52"
    }

    private fun displayPlayerName(name: String) {
        fragment_papin_game_name_single.text = name
        fragment_papin_game_name_doubleScreen1.text = name
        fragment_papin_game_name_doubleScreen2.text = name
    }

    private fun playCountDown() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.count_down)
        mediaPlayer.start()
    }

    private fun playLeverLesDeuxBras() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_les_deux_bras)
        mediaPlayer.start()
    }

    private fun playLeverLaJambeGauche() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_la_jambe_gauche)
        mediaPlayer.start()
    }

    private fun playLeverLeBrasDroit() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_le_bras_droit)
        mediaPlayer.start()
    }

    private fun playLeverLeBrasGauche() {
        mediaPlayer = MediaPlayer.create(context!!, R.raw.lever_le_bras_gauche)
        mediaPlayer.start()
    }

    private fun doubleScreenBtnIsClickable(enable: Boolean){
        if (enable){
            fragment_papin_game_double_screen_btn_single.isEnabled = true
            fragment_papin_game_double_screen_btn_doubleScreen1.isEnabled = true
            fragment_papin_game_double_screen_btn_doubleScreen2.isEnabled = true
        } else {
            fragment_papin_game_double_screen_btn_single.isEnabled = false
            fragment_papin_game_double_screen_btn_doubleScreen1.isEnabled = false
            fragment_papin_game_double_screen_btn_doubleScreen2.isEnabled = false
        }
    }

    private fun initRuleInfo(rule: String) {
        papinGame = false
        doubleScreenBtnIsClickable(true)

        if (!doubleScreen){
            fragment_papin_game_bottom_btn_single.visibility = View.INVISIBLE
            fragment_papin_game_info_btn_single.visibility = View.VISIBLE
        } else{
            fragment_papin_game_bottom_btn_doubleScreen1.visibility = View.INVISIBLE
            fragment_papin_game_bottom_btn_doubleScreen2.visibility = View.INVISIBLE
            fragment_papin_game_info_btn_doubleScreen1.visibility = View.VISIBLE
            fragment_papin_game_info_btn_doubleScreen2.visibility = View.VISIBLE
        }

        when (rule) {
            getString(R.string.PapinRuleTitle1) -> infoRule = getString(R.string.PapinRule1)
            getString(R.string.PapinRuleTitle2) -> infoRule = getString(R.string.PapinRule2)
            getString(R.string.PapinRuleTitle3) -> infoRule = getString(R.string.PapinRule3)
            getString(R.string.PapinRuleTitle4) -> infoRule = getString(R.string.PapinRule4)
            getString(R.string.PapinRuleTitle5) -> infoRule = getString(R.string.PapinRule5)
            getString(R.string.PapinRuleTitle6) -> infoRule = getString(R.string.PapinRule6)
            getString(R.string.PapinRuleTitle7) -> infoRule = getString(R.string.PapinRule7)
            getString(R.string.PapinRuleTitle8) -> infoRule = getString(R.string.PapinRule8)
            getString(R.string.PapinRuleTitle9) -> {
                infoRule = getString(R.string.PapinRule9)
                if (!doubleScreen){
                    fragment_papin_game_bottom_btn_single.visibility = View.VISIBLE
                    fragment_papin_game_bottom_btn_single.text = getString(R.string.PapinCountdown)
                } else{
                    fragment_papin_game_bottom_btn_doubleScreen1.visibility = View.VISIBLE
                    fragment_papin_game_bottom_btn_doubleScreen2.visibility = View.VISIBLE
                    fragment_papin_game_bottom_btn_doubleScreen1.text = getString(R.string.PapinCountdown)
                    fragment_papin_game_bottom_btn_doubleScreen2.text = getString(R.string.PapinCountdown)
                }

            }
            getString(R.string.PapinRuleTitle10) -> {
                doubleScreenBtnIsClickable(false)
                infoRule = getString(R.string.PapinRule10)
                playLeverLeBrasGauche()
            }
            getString(R.string.PapinRuleTitle11) -> {
                doubleScreenBtnIsClickable(false)
                infoRule = getString(R.string.PapinRule11)
                playLeverLeBrasDroit()
            }
            getString(R.string.PapinRuleTitle12) -> {
                doubleScreenBtnIsClickable(false)
                infoRule = getString(R.string.PapinRule12)
                playLeverLaJambeGauche()
            }
            getString(R.string.PapinRuleTitle13) -> {
                doubleScreenBtnIsClickable(false)
                infoRule = getString(R.string.PapinRule13)
                playLeverLesDeuxBras()
            }
            getString(R.string.PapinRuleTitle14) -> infoRule = getString(R.string.PapinRule14)
            getString(R.string.PapinRuleTitle15) -> infoRule = getString(R.string.PapinRule15)
            getString(R.string.PapinRuleTitle16) -> papinGame = true
            getString(R.string.PapinRuleTitle17) -> infoRule = getString(R.string.PapinRule17)
            getString(R.string.PapinRuleTitle18) -> infoRule = getString(R.string.PapinRule18)
            getString(R.string.PapinRuleTitle19) -> infoRule = getString(R.string.PapinRule19)
            getString(R.string.PapinRuleTitle20) -> infoRule = getString(R.string.PapinRule20)
            getString(R.string.PapinRuleTitle21) -> infoRule = getString(R.string.PapinRule21)
        }
    }

    private fun launchMainActivity() {
        val myIntent: Intent = Intent(activity, MainActivity::class.java)
        this.startActivity(myIntent)
    }

    private fun launchFragmentEndGame() {
        val pmuGameFragment = EndGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_container_frame, pmuGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }
}