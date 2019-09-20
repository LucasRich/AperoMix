package com.lucasri.aperomix.fragments

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.View.adapter.BeLuckyGameAdapter
import com.lucasri.aperomix.View.adapter.BeLuckyParamAdapter
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.utils.random
import kotlinx.android.synthetic.main.be_lucky_case_left.view.*
import kotlinx.android.synthetic.main.be_lucky_case_left.view.position1
import kotlinx.android.synthetic.main.fragment_be_lucky_game.*
import kotlin.collections.ArrayList

class BeLuckyGameFragment : Fragment(){

    private val playerList = ArrayList<Player>()
    private val caseList = ArrayList<View>()
    private var beLuckyGameAdapter: BeLuckyGameAdapter? = null
    private var iterator = 0
    private var previousCase = 0
    private var previousPosition = 0
    private var gameBegin = false
    private var nbBackCase = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_be_lucky_game, container, false)
        setHasOptionsMenu(true)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.initPlayerList()
        this.initCaseList()
        this.initCaseImg()
        this.initPlayerPositionDisplay()
        endButton.text = "Commencer !!"
        de.isEnabled = false

        endButton.setOnClickListener {
            if (!gameBegin){
                namePlayer.text = playerList[0].playerName
                endButton.text = "Tour fini"
                displayTxt.text = ""
                de.isEnabled = true
                gameBegin = true
            } else{
                if (nbBackCase < 0){
                    if (iterator == 0) movePlayer(nbBackCase, playerList[playerList.size -1]) else movePlayer(nbBackCase, playerList[iterator -1])
                    nbBackCase = 0
                }
                namePlayer.text = playerList[iterator].playerName
                displayTxt.text = ""
                de.isEnabled = true
            }
            endButton.isEnabled = false
        }

        de.setOnClickListener {
            launchDie(playerList[iterator])
            iterator++
            if (iterator > playerList.size -1) iterator = 0
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_pmu_game_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerList() {
        playerList.addAll(BeLuckyParamAdapter.playerList)
        beLuckyGameAdapter = BeLuckyGameAdapter(context, playerList)
        listPlayer!!.adapter = beLuckyGameAdapter
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun initCaseImg(){
        case1.case_img.setImageResource(R.drawable.ic_case1)
        case2.case_img.setImageResource(R.drawable.ic_case2)
        case3.case_img.setImageResource(R.drawable.ic_case3)
        case4.case_img.setImageResource(R.drawable.ic_case4_7)
        case5.case_img.setImageResource(R.drawable.ic_case5_14)
        case6.case_img.setImageResource(R.drawable.ic_case6_13)
        case7.case_img.setImageResource(R.drawable.ic_case4_7)
        case8.case_img.setImageResource(R.drawable.ic_case8)
        case9.case_img.setImageResource(R.drawable.ic_case9_10)
        case10.case_img.setImageResource(R.drawable.ic_case9_10)
        case11.case_img.setImageResource(R.drawable.ic_case11)
        case12.case_img.setImageResource(R.drawable.ic_case12)
        case13.case_img.setImageResource(R.drawable.ic_case6_13)
        case14.case_img.setImageResource(R.drawable.ic_case5_14)
        case15.case_img.setImageResource(R.drawable.ic_case15)
        case16.case_img.setImageResource(R.drawable.ic_case16)
        case17.case_img.setImageResource(R.drawable.ic_case17)
        case18.case_img.setImageResource(R.drawable.ic_case18)

    }

    private fun initCaseList(){
        caseList.add(case1)
        caseList.add(case1)
        caseList.add(case2)
        caseList.add(case3)
        caseList.add(case4)
        caseList.add(case5)
        caseList.add(case6)
        caseList.add(case7)
        caseList.add(case8)
        caseList.add(case9)
        caseList.add(case10)
        caseList.add(case11)
        caseList.add(case12)
        caseList.add(case13)
        caseList.add(case14)
        caseList.add(case15)
        caseList.add(case16)
        caseList.add(case17)
        caseList.add(case18)
    }

    private fun initPlayerPositionDisplay(){
        var iterator = 0
        for (player in playerList){
            helpInitPlayerPositionDisplay(iterator)
            player.beLuckyCasePosition = iterator+1
            iterator++
        }
    }

    private fun helpInitPlayerPositionDisplay(playerIndex: Int){
        when(playerIndex){
            0 -> case1.position1.setColorFilter(playerList[playerIndex].color)
            1 -> case1.position2.setColorFilter(playerList[playerIndex].color)
            2 -> case1.position3.setColorFilter(playerList[playerIndex].color)
            3 -> case1.position4.setColorFilter(playerList[playerIndex].color)
            4 -> case1.position5.setColorFilter(playerList[playerIndex].color)
            5 -> case1.position6.setColorFilter(playerList[playerIndex].color)
            6 -> case1.position7.setColorFilter(playerList[playerIndex].color)
            7 -> case1.position8.setColorFilter(playerList[playerIndex].color)
            8 -> case1.position9.setColorFilter(playerList[playerIndex].color)
            9 -> case1.position10.setColorFilter(playerList[playerIndex].color)
        }
    }

    private fun launchDie(player: Player) {
        var dieValue = 0
        de!!.isEnabled = false
        object : CountDownTimer(2000, 200) {
            override fun onTick(millisUntilFinished: Long) {

                dieValue = random(1, 6)
                displayDie(dieValue)
            }

            override fun onFinish() {
                endButton.isEnabled = true
                movePlayer(dieValue, player)
            }
        }.start()
    }

    private fun displayDie(dieValue: Int){
        when(dieValue){
            1 -> de.setImageResource(R.drawable.ic_die1)
            2 -> de.setImageResource(R.drawable.ic_die2)
            3 -> de.setImageResource(R.drawable.ic_die3)
            4 -> de.setImageResource(R.drawable.ic_die4)
            5 -> de.setImageResource(R.drawable.ic_die5)
            6 -> de.setImageResource(R.drawable.ic_die6)
        }
    }

    private fun movePlayer(nbCase: Int, player: Player){
        player.beLuckyCase = player.beLuckyCase + nbCase
        var previousCase: Int = player.beLuckyCase - nbCase

        if (player.beLuckyCase > 18) player.beLuckyCase = 18

        println("" + player.beLuckyCase + " : player next Case ")
        println("$previousCase : player previous Case")

        hidePawn(player.beLuckyCasePosition,caseList[previousCase])
        getPositionThanksOtherPlayerPosition(player, caseList[player.beLuckyCase])
        caseAction(player)
    }

    private fun caseAction(player: Player){
        when(player.beLuckyCase){
            2 -> {
                displayTxt.text = getString(R.string.BeLuckyCase2)
                iterator--
            }
            3 -> {
                displayTxt.text = getString(R.string.BeLuckyCase3)
            }
            4 -> {
                displayTxt.text = getString(R.string.BeLuckyCase4)
                nbBackCase = -3
            }
            5 -> {
            }
            6 -> {
            }
            7 -> {
                displayTxt.text = getString(R.string.BeLuckyCase7)
                nbBackCase = -6
            }
            8 -> {
                displayTxt.text = getString(R.string.BeLuckyCase8)
            }
            9 -> {
                displayTxt.text = getString(R.string.BeLuckyCase9)
            }
            10 -> {
            }
            11 -> {
                displayTxt.text = getString(R.string.BeLuckyCase11)
            }
            12 -> {
                displayTxt.text = getString(R.string.BeLuckyCase12)
            }
            13 -> {
            }
            14 -> {
            }
            15 -> {
                displayTxt.text = getString(R.string.BeLuckyCase15)
            }
            16 -> {
                displayTxt.text = getString(R.string.BeLuckyCase16)
            }
            17 -> {
                displayTxt.text = getString(R.string.BeLuckyCase17)
                nbBackCase = -7
            }
            18 -> {
                displayTxt.text = getString(R.string.BeLuckyCase18)
            }
        }
    }

    private fun getPositionThanksOtherPlayerPosition(player: Player, case: View){
        val positionUse = ArrayList<Int>()
        positionUse.add(10000)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)
        positionUse.add(0)

        for (players in playerList){
            if (players.beLuckyCase == player.beLuckyCase && players.playerName != player.playerName) positionUse[players.beLuckyCasePosition] = players.beLuckyCasePosition
        }

        var position = positionUse.indexOf(0)

        player.beLuckyCasePosition = position
        displayPawn(position, case, player.color)
    }

    private fun displayPawn(position: Int, case: View, color: Int){
        when(position){
            1 -> case.position1.setColorFilter(color)
            2 -> case.position2.setColorFilter(color)
            3 -> case.position3.setColorFilter(color)
            4 -> case.position4.setColorFilter(color)
            5 -> case.position5.setColorFilter(color)
            6 -> case.position6.setColorFilter(color)
            7 -> case.position7.setColorFilter(color)
            8 -> case.position8.setColorFilter(color)
            9 -> case.position9.setColorFilter(color)
            10 -> case.position10.setColorFilter(color)
        }
    }

    private fun hidePawn(position: Int, case: View){
        when(position){
            1 -> case.position1.colorFilter = null
            2 -> case.position2.colorFilter = null
            3 -> case.position3.colorFilter = null
            4 -> case.position4.colorFilter = null
            5 -> case.position5.colorFilter = null
            6 -> case.position6.colorFilter = null
            7 -> case.position7.colorFilter = null
            8 -> case.position8.colorFilter = null
            9 -> case.position9.colorFilter = null
            10 -> case.position10.colorFilter = null
        }
    }
}