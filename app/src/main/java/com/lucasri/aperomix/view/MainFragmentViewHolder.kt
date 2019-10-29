package com.lucasri.aperomix.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.model.Player
import kotlinx.android.synthetic.main.main_fragment_item.view.*

class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithPlayerList(playerList: Player) {
        if (playerList.playerName.contains("Joueur")) {
            view.enter_players.hint = playerList.playerName
        } else {
            view.enter_players.setText(playerList.playerName)
        }

        view.enter_players.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                playerList.playerName = view.enter_players.text.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }
}
