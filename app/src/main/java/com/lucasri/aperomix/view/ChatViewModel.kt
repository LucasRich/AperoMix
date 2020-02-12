package com.lucasri.aperomix.view

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.lucasri.aperomix.database.repository.ChatDataRepository
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.models.Message
import com.lucasri.aperomix.models.User
import java.util.*
import java.util.concurrent.Executor

class ChatViewModel(private val chatDataSource: ChatDataRepository, private val executor: Executor) : ViewModel() {

    // -------------------
    // INSERT
    // -------------------

    fun createChatInFirestore(message: Message){
        executor.execute {
            chatDataSource.createMessageForChat(message)
        }
    }

    // -------------------
    // GET
    // -------------------

    fun getAllMessages(): Query{
        return chatDataSource.getAllMessageForChat()
    }

}
