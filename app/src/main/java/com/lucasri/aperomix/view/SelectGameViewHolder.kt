package com.lucasri.aperomix.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.model.Game
import kotlinx.android.synthetic.main.fragment_select_game_item.view.*

class SelectGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithGame(game: Game) {
        view.imgGame.setImageResource(game.image!!)
        view.GameName.text = game.name
    }
}