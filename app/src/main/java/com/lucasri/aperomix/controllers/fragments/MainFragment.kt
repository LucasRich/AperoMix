package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.view.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import com.lucasri.aperomix.controllers.activities.AccountActivity
import com.lucasri.aperomix.utils.launchActivity
import com.lucasri.aperomix.utils.longToast
import com.lucasri.aperomix.utils.toast

class MainFragment : Fragment(), MainFragmentAdapter.Listener {

    private lateinit var adapter: MainFragmentAdapter
    private var iterator: Boolean = true
    private lateinit var auth: FirebaseAuth

    companion object {
        var playerList = mutableListOf<Player>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        this.initPlayerList()
        this.configureRecyclerView(true)

        fragment_main_start_btn.setOnClickListener {
            launchFragment(SelectGameFragment())
        }

        fragment_main_addPlayer.setOnClickListener {
            addPlayer()
        }

        fragment_main_account_btn.setOnClickListener {
            context!!.launchActivity(AccountActivity())
        }

        fragment_main_share_btn.setOnClickListener {
            shareApp()
        }

        fragment_main_chat_btn.setOnClickListener{
            if (auth.currentUser != null) launchFragment(ChatFragment()) else context!!.longToast(getString(R.string.Chat_error))
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
                player.playerName = getString(R.string.fragment_main_player) + " $i"
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

    private fun shareApp() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_txt))
        startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    private fun addPlayer() {
        btnAddAnimation()
        val player = Player()
        var iterator = 1

        //check if the player does not already exist
        do if (playerListSameName(getString(R.string.fragment_main_player) + " $iterator")) iterator++ while (playerListSameName(getString(R.string.fragment_main_player) + " $iterator"))

        player.playerName = getString(R.string.fragment_main_player) + " $iterator"
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

    private fun launchFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame, fragment).commit()
    }
}
