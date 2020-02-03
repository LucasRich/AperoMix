package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.GameDetailsViewHolder

class GameDetailsAdapter (private var pictureList: List<Int>) : RecyclerView.Adapter<GameDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailsViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_game_details_item, parent, false)

        return GameDetailsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GameDetailsViewHolder, position: Int) {
        viewHolder.updateWithPictureList(this.pictureList[position])
    }

    override fun getItemCount(): Int {
        return this.pictureList.size
    }
}