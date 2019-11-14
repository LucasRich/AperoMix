package com.lucasri.aperomix.api

import com.google.android.gms.tasks.Task
import android.provider.SyncStateContract.Helpers.update
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference
import com.lucasri.aperomix.model.User


object UserHelper {

    private const val COLLECTION_NAME = "users"

    // --- COLLECTION REFERENCE ---

    fun getUsersCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    // --- CREATE ---

    fun createUser(uid: String, username: String,  email: String, birthDay: String): Task<Void> {
        val userToCreate = User(uid, username, email, birthDay)
        return UserHelper.getUsersCollection().document(uid).set(userToCreate)
    }

    // --- GET ---

    fun getUser(uid: String): Task<DocumentSnapshot> {
        return UserHelper.getUsersCollection().document(uid).get()
    }

    // --- UPDATE ---

    fun updateUsername(username: String, uid: String): Task<Void> {
        return UserHelper.getUsersCollection().document(uid).update("username", username)
    }

    // --- DELETE ---

    fun deleteUser(uid: String): Task<Void> {
        return UserHelper.getUsersCollection().document(uid).delete()
    }

}