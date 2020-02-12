package com.lucasri.aperomix.database.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.lucasri.aperomix.models.User

class UserDataRepository() {

    // --- COLLECTION REFERENCE ---

    private fun getUsersCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }

    // -------------------
    // INSERT
    // -------------------

    fun createUserInFirestore(user: User){
        getUsersCollection().document(user.uid!!)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document!!.exists()) {
                    } else getUsersCollection().document(user.uid!!).set(user)
                }
            }
    }

    // -------------------
    // GET
    // -------------------

    fun getUser(uid: String): Task<DocumentSnapshot> {
        return getUsersCollection().document(uid).get()
    }

    fun getAllUser(): Query {
        return getUsersCollection()
            .orderBy("firstname")
    }

    // -------------------
    // UPDATE
    // -------------------

    fun updatePapinCounter(count: Int, uid: String): Task<Void> {
        return getUsersCollection().document(uid).update("papinGameCounter", count)
    }

    fun updatePmuCounter(count: Int, uid: String): Task<Void> {
        return getUsersCollection().document(uid).update("pmuGameCounter", count)
    }

    fun updateBeLuckyCounter(count: Int, uid: String): Task<Void> {
        return getUsersCollection().document(uid).update("beLuckyGameCounter", count)
    }
}