package com.lucasri.aperomix.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.models.Player
import kotlinx.android.synthetic.main.fragment_be_lucky_param_item.view.playerName

class BeLuckyGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithPlayerList(playerList: Player) {
        view.playerName.text = playerList.playerName
        view.playerName.setTextColor(playerList.color)
    }
}