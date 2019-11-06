package com.lucasri.aperomix.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucasri.aperomix.R
import com.lucasri.aperomix.controllers.activities.GameContainer
import com.lucasri.aperomix.model.Game
import com.lucasri.aperomix.view.adapter.SelectGameAdapter
import kotlinx.android.synthetic.main.fragment_select_game.*
import androidx.recyclerview.widget.GridLayoutManager
import android.view.animation.AnimationUtils
import com.lucasri.aperomix.utils.ItemClickSupport


class SelectGameFragment : Fragment() {

    private var adapter: SelectGameAdapter? = null
    var gameList = mutableListOf<Game>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initGameList()
        this.configureRecyclerView()
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    private fun configureRecyclerView() {
        //INIT
        this.adapter = SelectGameAdapter(gameList)
        this.fragment_select_game_recycler_view.adapter = this.adapter
        this.fragment_select_game_recycler_view.layoutManager = GridLayoutManager(activity, 2)

        //ANIM
        val controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation_fall_down)
        this.fragment_select_game_recycler_view.layoutAnimation = controller
        this.fragment_select_game_recycler_view.scheduleLayoutAnimation()

        //CLICK
        ItemClickSupport.addTo(fragment_select_game_recycler_view, R.layout.fragment_select_game_item)
                .setOnItemClickListener { recyclerView, position, v ->
                    DetailsGamesFragment
                            .newInstance(gameList[position].name)
                            .show(activity!!.supportFragmentManager, "DETAILS_GAMES")
                }
    }

    private fun initGameList(){
        gameList.add(Game("Papin Game", R.drawable.logo_v1))
        gameList.add(Game("Le PMU", R.drawable.logo_v1))
        gameList.add(Game("Be lucky Game", R.drawable.logo_v1))
    }

    // ---------------------
    // UTILS
    // ---------------------

    private fun launchGame(gameTitle: String, viewClicked: View) {

        val myIntent = Intent(context, GameContainer::class.java)
        val bundle = Bundle()

        bundle.putString("startParameter", gameTitle)
        myIntent.putExtras(bundle)

        startActivity(myIntent)
    }
}