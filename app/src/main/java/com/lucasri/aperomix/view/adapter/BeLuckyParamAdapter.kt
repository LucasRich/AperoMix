package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.view.BeLuckyParamViewHolder

class BeLuckyParamAdapter (var playerList: MutableList<Player>) : RecyclerView.Adapter<BeLuckyParamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeLuckyParamViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_be_lucky_param_item, parent, false)

        return BeLuckyParamViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BeLuckyParamViewHolder, position: Int) {
        viewHolder.updateWithPlayerList(this.playerList[position])
    }

    override fun getItemCount(): Int {
        return this.playerList.size
    }
}