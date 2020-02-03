package com.lucasri.aperomix.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.controllers.fragments.MainFragment
import com.lucasri.aperomix.models.Player
import com.lucasri.aperomix.view.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.fragment_main_item.view.*
import java.lang.ref.WeakReference

class MainFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view
    private var callbackWeakRef: WeakReference<MainFragmentAdapter.Listener>? = null


    fun updateWithPlayerList(player: Player, position: Int, callback: MainFragmentAdapter.Listener) {

        var list: MutableList<Player> = MainFragment.playerList


        if (player.playerName.contains("Joueur")) {
            view.fragment_main_item_enter_players.hint = player.playerName
        } else {
            view.fragment_main_item_enter_players.setText(player.playerName)
        }

        view.fragment_main_item_enter_players.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                list[position].playerName = view.fragment_main_item_enter_players.text.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        this.callbackWeakRef = WeakReference(callback)

        view.fragment_main_item_clear.setOnClickListener {
            val callbacks = callbackWeakRef!!.get()
            callback.onClickDeleteButton(adapterPosition)
        }
    }
}
