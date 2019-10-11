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
import com.lucasri.aperomix.view.PmuParamViewHolder

class PmuParamAdapter (var playerList: MutableList<Player>) : RecyclerView.Adapter<PmuParamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PmuParamViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.pmu_param_item, parent, false)

        return PmuParamViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PmuParamViewHolder, position: Int) {
        viewHolder.updateWithPlayerList(this.playerList[position])
    }

    override fun getItemCount(): Int {
        return this.playerList.size
    }
}