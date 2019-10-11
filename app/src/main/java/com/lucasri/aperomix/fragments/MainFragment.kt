package com.lucasri.aperomix.fragments

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

class MainFragment : Fragment() {

    lateinit var adapter: MainFragmentAdapter

    companion object {
        fun newInstance(): MainFragment{
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
        this.configureRecyclerView()

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

    private fun configureRecyclerView() {
        this.adapter = MainFragmentAdapter(playerList)
        this.main_fragment_recycler_view.adapter = this.adapter
        this.main_fragment_recycler_view.layoutManager = LinearLayoutManager(context)
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
    // UTILS
    // ---------------------

    private fun addPlayer() {
        val player = Player()
        player.playerName = "Joueur ${playerList.size+1}"
        playerList.add(player)

        adapter.updateData(playerList)
    }

    private fun launchSelectGameFragment() {
        val fragment = SelectGameFragment()
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_main_frame, fragment)
        transaction.commit()
    }
}
