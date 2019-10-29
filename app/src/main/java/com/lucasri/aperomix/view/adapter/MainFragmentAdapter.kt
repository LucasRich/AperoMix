package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.view.MainFragmentViewHolder

class MainFragmentAdapter (var playerList: MutableList<Player>) : RecyclerView.Adapter<MainFragmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.main_fragment_item, parent, false)

        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MainFragmentViewHolder, position: Int) {
        viewHolder.updateWithPlayerList(this.playerList[position])
    }

    override fun getItemCount(): Int {
        return this.playerList.size
    }

    fun updateData(playerList: MutableList<Player>) {
        this.playerList = playerList
        this.notifyDataSetChanged()
    }
}