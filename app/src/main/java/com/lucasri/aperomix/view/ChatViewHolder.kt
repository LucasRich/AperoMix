package com.lucasri.aperomix.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucasri.aperomix.models.Message
import kotlinx.android.synthetic.main.fragment_chat_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun updateWithMessage(message: Message, currentUserId: String) {
        if (message.userSender!!.uid == currentUserId) initViewMyMessage(message) else initViewOtherMessage(message)
    }

    // -------------------
    // UTILS
    // -------------------

    private fun initViewMyMessage(message: Message){
        itemView.fragment_chat_item_layout_otherMessage.visibility = View.GONE
        itemView.fragment_chat_item_layout_myMessage.visibility = View.VISIBLE

        itemView.fragment_chat_item_myMessage.text = message.message
        itemView.fragment_chat_item_myInfos.text = getGoodDateFormat(message.dateCreated)
    }

    private fun initViewOtherMessage(message: Message){
        itemView.fragment_chat_item_layout_myMessage.visibility = View.GONE
        itemView.fragment_chat_item_layout_otherMessage.visibility = View.VISIBLE

        itemView.fragment_chat_item_otherMessage.text = message.message
        itemView.fragment_chat_item_otherInfos.text = "${message.userSender!!.userName}\n${getGoodDateFormat(message.dateCreated)}"
    }

    private fun getGoodDateFormat(date: Date?): String? {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return simpleDateFormat.format(date)
    }
}
