package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.view.BeLuckyGameViewHolder

class BeLuckyGameAdapter (var playerList: MutableList<Player>) : RecyclerView.Adapter<BeLuckyGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeLuckyGameViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_be_lucky_game_item, parent, false)

        return BeLuckyGameViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BeLuckyGameViewHolder, position: Int) {
        viewHolder.updateWithPlayerList(this.playerList[position])
    }

    override fun getItemCount(): Int {
        return this.playerList.size
    }
}