package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.view.BeLuckyParamViewHolder
import com.lucasri.aperomix.view.GamesDetailsViewHolder

class GamesDetailsAdapter (private var pictureList: List<Int>) : RecyclerView.Adapter<GamesDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesDetailsViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_details_games_item, parent, false)

        return GamesDetailsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GamesDetailsViewHolder, position: Int) {
        viewHolder.updateWithPictureList(this.pictureList[position])
    }

    override fun getItemCount(): Int {
        return this.pictureList.size
    }
}