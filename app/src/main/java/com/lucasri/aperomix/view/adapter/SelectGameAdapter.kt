package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Game
import com.lucasri.aperomix.view.SelectGameViewHolder

class SelectGameAdapter(var gameList: List<Game>) : RecyclerView.Adapter<SelectGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectGameViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_select_game_item, parent, false)

        return SelectGameViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SelectGameViewHolder, position: Int) {
        viewHolder.updateWithGame(this.gameList[position])
    }

    override fun getItemCount(): Int {
        return this.gameList.size
    }
}
