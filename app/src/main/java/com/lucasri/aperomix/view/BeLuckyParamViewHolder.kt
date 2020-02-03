package com.lucasri.aperomix.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Player
import com.thebluealliance.spectrum.SpectrumDialog
import kotlinx.android.synthetic.main.fragment_be_lucky_param_item.view.*

class BeLuckyParamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val view: View = view

    fun updateWithPlayerList(playerList: Player) {
        view.playerName.text = playerList.playerName

        view.beLucky_Param_input_container.setOnClickListener {

            val bu = SpectrumDialog.Builder(view.context)
            bu.setColors(R.array.player_colors)
            bu.setTitle("Choisis ta couleur")
            bu.setSelectedColor(playerList.color)
            bu.setOnColorSelectedListener { _, color1 ->

                playerList.color = color1
                view.playerColor.setColorFilter(color1)
            }
            bu.build().show((view.context as AppCompatActivity).supportFragmentManager, "tag")

        }
    }
}