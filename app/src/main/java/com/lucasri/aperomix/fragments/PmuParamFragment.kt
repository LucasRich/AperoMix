package com.lucasri.aperomix.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.View.adapter.PlayerAdapter
import com.lucasri.aperomix.View.adapter.PmuParamAdapter
import com.lucasri.aperomix.model.Player
import kotlinx.android.synthetic.main.fragment_pmu_param.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import java.util.*

class PmuParamFragment : Fragment() {

    private var pmuParamAdapter: PmuParamAdapter? = null
    private var playerList = ArrayList<Player>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.initPlayerListView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_pmu_param, container, false)
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
            R.id.done -> launchFragmentPmuGame()
            R.id.rule -> displayRule()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerListView() {
        for (i in PlayerAdapter.playerList.indices) {
            val player = Player()
            player.picNbDrink = 0
            player.playerName = PlayerAdapter.playerList[i].playerName
            playerList.add(player)
        }

        pmuParamAdapter = PmuParamAdapter(context, playerList)
        listPlayer!!.adapter = pmuParamAdapter
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun displayRule() {
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

    private fun launchFragmentPmuGame() {
        val pmuGameFragment = PmuGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_frame, pmuGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }
}
