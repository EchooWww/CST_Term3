package com.example.lecturedtc.ui.state

import androidx.compose.runtime.toMutableStateList
import com.example.lecturedtc.data.LocalUser
import com.example.lecturedtc.data.UserRepository

class UserState(private val repository: UserRepository) {
    var users = repository.getAllEntities().toMutableStateList()

    fun add(user: LocalUser) {
        repository.insertEntity(user)
    }

    fun delete(user: LocalUser) {
        users.remove(user)
        repository.deleteEntity(user)
    }

    fun refresh() {
        users.apply{ // using apply so the recomposition is only triggered once for the 2 function calls below
            clear()
            addAll(repository.getAllEntities())
        }
    }
}