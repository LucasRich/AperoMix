package com.lucasri.aperomix.database.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucasri.aperomix.database.repository.ChatDataRepository
import com.lucasri.aperomix.database.repository.UserDataRepository
import com.lucasri.aperomix.view.ChatViewModel
import com.lucasri.aperomix.view.UserViewModel
import java.util.concurrent.Executor

class ChatViewModelFactory(private val chatDataSource: ChatDataRepository, private val executor: Executor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(chatDataSource, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}