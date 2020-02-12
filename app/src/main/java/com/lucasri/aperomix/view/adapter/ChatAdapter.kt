package com.lucasri.aperomix.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.lucasri.aperomix.R
import com.lucasri.aperomix.models.Message
import com.lucasri.aperomix.models.User
import com.lucasri.aperomix.view.ChatViewHolder

class ChatAdapter (options: FirestoreRecyclerOptions<Message>, callbacks: Listener, private val idCurrentUser: String) : FirestoreRecyclerAdapter<Message, ChatViewHolder>(options) {

    interface Listener {
        fun onClickDeleteButton(position: Int)
    }

    private var callback: Listener? = callbacks


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.fragment_chat_item, parent, false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ChatViewHolder, position: Int, @NonNull model: Message) {
        holder.updateWithMessage(model, this.idCurrentUser)
    }

    fun updateData() {
        this.notifyDataSetChanged()
    }
}