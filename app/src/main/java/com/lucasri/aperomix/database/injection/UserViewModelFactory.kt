package com.lucasri.aperomix.database.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.view.UserViewModel
import java.util.concurrent.Executor

class UserViewModelFactory(private val userDataSource: UserDataRepository, private val executor: Executor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userDataSource, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}