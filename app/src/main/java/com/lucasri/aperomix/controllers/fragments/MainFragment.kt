package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasri.aperomix.R
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.view.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import android.view.animation.AnimationUtils
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.utils.toast

class MainFragment : Fragment(), MainFragmentAdapter.Listener {

    lateinit var adapter: MainFragmentAdapter
    private var iterator: Boolean = true

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
        var playerList = mutableListOf<Player>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initPlayerList()
        this.configureRecyclerView(true)

        fragment_main_start_btn.setOnClickListener {
            launchSelectGameFragment()
        }

        fragment_main_addPlayer.setOnClickListener {
            btnAddAnimation()
            addPlayer()
        }

        fragment_main_account_btn.setOnClickListener {
            launchAccountActivity()
        }
    }

    private fun btnAddAnimation(){
        if(iterator) {
            fragment_main_addPlayer.animate().rotation(90f).duration = 600
            iterator = false
        } else {
            fragment_main_addPlayer.animate().rotation(-90f).duration = 600
            iterator = true
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView(animation: Boolean) {
        //INIT
        this.adapter = MainFragmentAdapter(playerList, this)
        this.fragment_main_recycler_view.adapter = this.adapter
        this.fragment_main_recycler_view.layoutManager = LinearLayoutManager(context)

        //ANIM
        if (animation){
            val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down)
            this.fragment_main_recycler_view.layoutAnimation = controller
            this.fragment_main_recycler_view.scheduleLayoutAnimation()
        }

    }

    private fun initPlayerList(){
        if (playerList.size == 0) {
            for (i in 1..3) {
                val player = Player()
                player.playerName = "Joueur $i"
                playerList.add(player)
            }
        }
    }

    // ---------------------
    // ACTION
    // ---------------------

    override fun onClickDeleteButton(position: Int) {
        if (playerList.size > 3){
            playerList.removeAt(position)
            configureRecyclerView(false)
        } else {
            context!!.toast(getString(R.string.fragment_main_delete_player_error))
        }
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun addPlayer() {
        val player = Player()
        var iterator = playerList.size +1

        do {
            if (playerListSameName("Joueur $iterator")) iterator++
        } while (playerListSameName("Joueur $iterator"))

        player.playerName = "Joueur $iterator"
        playerList.add(player)
        configureRecyclerView(false)
    }

    private fun playerListSameName(playerName: String): Boolean{
        var result: Boolean = false
        for (player in playerList){
            if (player.playerName.contains(playerName))  result = true
        }
        return result
    }

    private fun launchSelectGameFragment() {
        val fragment = SelectGameFragment()
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, fragment)
        transaction.commit()
    }

    private fun launchAccountActivity() {
        val myIntent: Intent = Intent(context!!, AccountActivity::class.java)
        this.startActivity(myIntent)
    }
}
