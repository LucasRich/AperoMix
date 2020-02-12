package com.lucasri.aperomix.database.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.lucasri.aperomix.models.Message
import com.lucasri.aperomix.models.User
import com.lucasri.aperomix.utils.getCurrentDate
import java.util.*

class ChatDataRepository() {

    // --- COLLECTION REFERENCE ---

    private fun getChatCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("chat")
    }

    // -------------------
    // INSERT
    // -------------------

    fun createMessageForChat(message: Message): Task<DocumentReference> {
        message.dateCreated = getCurrentDate()
        return getChatCollection().add(message)
    }

    // -------------------
    // GET
    // -------------------

    fun getAllMessageForChat(): Query {
        return getChatCollection()
                .orderBy("dateCreated")
    }

}