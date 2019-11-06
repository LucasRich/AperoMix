package com.lucasri.aperomix.controllers.fragments

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.adapter.BeLuckyParamAdapter
import com.lucasri.aperomix.utils.toast
import kotlinx.android.synthetic.main.fragment_be_lucky_param.*
import kotlinx.android.synthetic.main.info_dialog.view.*

class BeLuckyParamFragment : Fragment() {

    private var adapter: BeLuckyParamAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.initPlayerListView()
        this.configureRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_be_lucky_param, container, false)
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView() {
        //INIT
        this.adapter = BeLuckyParamAdapter(MainFragment.playerList)
        this.beLucky_param_recycler_view.adapter = this.adapter
        this.beLucky_param_recycler_view.layoutManager = LinearLayoutManager(context)

        //ANIM
        val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down)
        this.beLucky_param_recycler_view.layoutAnimation = controller
        this.beLucky_param_recycler_view.scheduleLayoutAnimation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_pmu_param_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done -> clickOnDone()
            R.id.rule -> displayRule()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPlayerListView() {
        for (player in MainFragment.playerList) {
            player.color = -24576
            player.beLuckyCase = 1
        }
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun clickOnDone(){
        if (MainFragment.playerList.size < 11){
            if (allColorAreDifferente()){
                launchFragmentBeLuckyGame()
            } else context!!.toast("Vous ne pouvez pas sélectionner deux fois la même couleur !")

        } else context!!.toast("Vous ne pouvez pas jouer à plus de dix !")

    }

    private fun allColorAreDifferente() : Boolean {
        for (player in MainFragment.playerList) {
            for (players in MainFragment.playerList) {
                if (player.playerName !== players.playerName){
                    if (player.color == players.color) {
                        return false
                    }
                }
            }
        }
        return true
    }

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

    private fun launchFragmentBeLuckyGame() {
        val beLuckyGameFragment = BeLuckyGameFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_game_container_frame, beLuckyGameFragment, "findThisFragment")
                .addToBackStack(null)
                .commit()
    }
}
