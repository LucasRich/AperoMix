package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

import com.lucasri.aperomix.model.Player
import com.lucasri.aperomix.R

import java.util.ArrayList

@Suppress("NAME_SHADOWING")
class PlayerAdapter(var playerList: ArrayList<Player>) : BaseAdapter() {

    internal class ViewHolder {
        var playerEditText: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = parent.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.main_fragment_item, null, true)

            holder.playerEditText = convertView!!.findViewById<View>(R.id.enter_players) as EditText

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        if (playerList[position].playerName.contains("Joueur")) {
            holder.playerEditText!!.hint = playerList[position].playerName
        } else {
            holder.playerEditText!!.text = playerList[position].playerName
        }

        holder.playerEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                playerList[position].playerName = holder.playerEditText!!.text.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        return convertView
    }

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getCount(): Int {
        return playerList.size
    }

    override fun getItem(position: Int): Any {
        return playerList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}
