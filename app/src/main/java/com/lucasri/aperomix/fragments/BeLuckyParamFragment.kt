package com.lucasri.aperomix.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.View.adapter.BeLuckyParamAdapter
import com.lucasri.aperomix.View.adapter.PlayerAdapter
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.utils.toast
import kotlinx.android.synthetic.main.fragment_be_lucky_param.*
import kotlinx.android.synthetic.main.info_dialog.view.*

class BeLuckyParamFragment : Fragment() {

    private var beLuckyParamAdapter: BeLuckyParamAdapter? = null
    private var playerList = ArrayList<Player>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.initPlayerListView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_be_lucky_param, container, false)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_pmu_param_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.done -> clickOnDone()
            R.id.rule -> displayRule()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerListView() {
        for (i in PlayerAdapter.playerList.indices) {
            val player = Player()
            player.color = -24576
            player.beLuckyCase = 1
            player.playerName = PlayerAdapter.playerList[i].playerName
            playerList.add(player)
        }

        beLuckyParamAdapter = BeLuckyParamAdapter(context, playerList)
        listPlayer!!.adapter = beLuckyParamAdapter
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun clickOnDone(){
        if (playerList.size < 11){
            if (allColorAreDifferente()){
                launchFragmentBeLuckyGame()
            } else context!!.toast("Vous ne pouvez pas sélectionner deux fois la même couleur !")

        } else context!!.toast("Vous ne pouvez pas jouer à plus de dix !")

    }

    private fun allColorAreDifferente() : Boolean {
        for (player in playerList) {
            for (players in playerList) {
                if (player.playerName !== players.playerName){
                    if (player.color == players.color) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun displayRule() {
        val dialogBuilder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.info_dialog, null)
        dialogBuilder.setView(dialogView)

        //INIT
        dialogView.text_info.text = "Que faire ?\n\nCe jeu est un jeu de hasard. Miser le nombre de gorgées de votre choix sur la carte de votre choix. Une fois misé, buvez le nombre de gorgées que vous avez misé. Ensuite cliquez en haut à droite et laissez le jeu faire, vous découvrirez si vous avez misé sur les bons chevaux !"

        //DISPLAY DIALOG
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun launchFragmentBeLuckyGame() {
        val beLuckyGameFragment = BeLuckyGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_frame, beLuckyGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }
}
