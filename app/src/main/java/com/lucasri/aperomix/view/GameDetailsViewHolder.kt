package com.lucasri.aperomix.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game_details_item.view.*

class GameDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithPictureList(picture: Int) {
        view.fragment_details_item_img.setImageResource(picture)
    }
}