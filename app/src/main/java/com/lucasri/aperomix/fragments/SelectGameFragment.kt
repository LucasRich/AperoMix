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
import com.lucasri.aperomix.activities.GameContainer
import com.lucasri.aperomix.model.Game
import com.lucasri.aperomix.utils.setToolbarTitle
import kotlinx.android.synthetic.main.activity_game_container.*
import kotlinx.android.synthetic.main.fragment_select_game.*
import kotlinx.android.synthetic.main.game_entry.view.*

class SelectGameFragment : Fragment() {

    var adapter: GameAdapter? = null
    var gameList = ArrayList<Game>()

    companion object {
        fun newInstance(): SelectGameFragment{
            return SelectGameFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameList.add(Game("Papin Game", R.drawable.logo_v1))
        gameList.add(Game("Le PMU", R.drawable.logo_v1))
        gameList.add(Game("Be lucky Game", R.drawable.logo_v1))
        adapter = GameAdapter(context!!, gameList)

        gvGames.adapter = adapter
    }

    // ---------------------
    // ADAPTER
    // ---------------------

    class GameAdapter : BaseAdapter, ListAdapter {
        var gameList = ArrayList<Game>()
        var context: Context? = null

        constructor(context: Context, gameList: ArrayList<Game>) : super() {
            this.context = context
            this.gameList = gameList
        }

        override fun getCount(): Int {
            return gameList.size
        }

        override fun getItem(position: Int): Any {
            return gameList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val game = this.gameList[position]

            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var gameView = inflater.inflate(R.layout.game_entry, null)
            gameView.imgGame.setImageResource(game.image!!)
            gameView.tvName.text = game.name!!

            gameView.setOnClickListener {
                launchGame(game.name.toString())
            }

            return gameView
        }

        // ---------------------
        // UTILS
        // ---------------------

        private fun launchGame(gameTitle: String) {

            val myIntent = Intent(context, GameContainer::class.java)
            val bundle = Bundle()

            bundle.putString("startParameter", gameTitle)
            myIntent.putExtras(bundle)

            context!!.startActivity(myIntent)
        }
    }
}