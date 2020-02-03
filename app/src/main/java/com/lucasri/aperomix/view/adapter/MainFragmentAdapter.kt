package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.view.MainFragmentViewHolder

class MainFragmentAdapter (var playerList: MutableList<Player>, callbacks: Listener) : RecyclerView.Adapter<MainFragmentViewHolder>() {

    interface Listener {
        fun onClickDeleteButton(position: Int)
    }

    private var callback: Listener? = callbacks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_main_item, parent, false)

        return MainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MainFragmentViewHolder, position: Int) {
        viewHolder.updateWithPlayerList(this.playerList[position], position, this.callback!!)
    }

    override fun getItemCount(): Int {
        return this.playerList.size
    }

    fun updateData() {
        this.notifyDataSetChanged()
    }
}