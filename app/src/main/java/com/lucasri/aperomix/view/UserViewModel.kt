package com.lucasri.aperomix.view

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.models.User
import java.util.concurrent.Executor

class UserViewModel(private val userDataSource: UserDataRepository, private val executor: Executor) : ViewModel() {

    // -------------------
    // INSERT
    // -------------------

    fun createUserInFirestore(user: User): String{
        var result = ""
        executor.execute { result = userDataSource.createUserInFirestore(user) }
        return result
    }

    // -------------------
    // GET
    // -------------------

    fun getUser(uid: String): Task<DocumentSnapshot>{
        return userDataSource.getUser(uid)
    }

    fun getAllUser(): Task<QuerySnapshot> {
        return userDataSource.getAllUser()
    }

    // -------------------
    // UPDATE
    // -------------------

    fun updatePapinGameCounter(count: Int, uid: String){
        executor.execute { userDataSource.updatePapinCounter(count, uid) }
    }

    fun updatePmuGameCounter(count: Int, uid: String){
        executor.execute { userDataSource.updatePmuCounter(count, uid) }
    }

    fun updateBeLuckyGameCounter(count: Int, uid: String){
        executor.execute { userDataSource.updateBeLuckyCounter(count, uid) }
    }
}
