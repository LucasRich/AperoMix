package com.lucasri.aperomix.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.View.adapter.PlayerAdapter
import com.lucasri.aperomix.activities.GameContainer
import com.lucasri.aperomix.activities.PapinGame
import com.lucasri.aperomix.model.Game
import com.lucasri.aperomix.model.Player
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_select_game.*
import kotlinx.android.synthetic.main.game_entry.view.*

class MainFragment : Fragment() {

    private var playerAdapter: PlayerAdapter? = null
    var playerList = java.util.ArrayList<Player>()
    private var counter = 4

    companion object {
        fun newInstance(): MainFragment{
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initPlayerListView()
        //this.configureToolbar();

        startButton.setOnClickListener {
            launchSelectGameFragment()
        }

        plus_one.setOnClickListener {
            addPlayer()
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun initPlayerListView() {
        if (PlayerAdapter.playerList != null) {
            playerList.addAll(PlayerAdapter.playerList)
        } else {
            for (i in 1..3) {
                val player = Player()
                player.playerName = "Joueur $i"
                playerList.add(player)
            }
        }

        println(playerList)
        playerAdapter = PlayerAdapter(context, playerList)
        list!!.adapter = playerAdapter
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun addPlayer() {
        val player = Player()
        player.playerName = "Joueur $counter"
        playerList.add(player)

        playerAdapter = PlayerAdapter(context, playerList)
        list!!.adapter = playerAdapter
        counter++
    }

    private fun launchSelectGameFragment() {
        val fragment = SelectGameFragment()
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, fragment)
        transaction.commit()
    }
}
