package com.lucasri.aperomix.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewParent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.model.Player
import com.thebluealliance.spectrum.SpectrumDialog
import kotlinx.android.synthetic.main.fragment_be_lucky_game_item.view.*
import kotlinx.android.synthetic.main.fragment_be_lucky_param_item.view.*
import kotlinx.android.synthetic.main.fragment_be_lucky_param_item.view.playerName
import kotlinx.android.synthetic.main.main_fragment_item.view.*

class BeLuckyGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithPlayerList(playerList: Player) {
        view.playerName.text = playerList.playerName
        view.playerName.setTextColor(playerList.color)
    }
}