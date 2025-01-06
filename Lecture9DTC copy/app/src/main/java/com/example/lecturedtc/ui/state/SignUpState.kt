package com.example.lecturedtc.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lecturedtc.data.LocalUser

class SignUpState {
    var uid by mutableStateOf("")
    var validUid by mutableStateOf(false)
    val onUidChange: (String) -> Unit = {
        uid = it
        validUid = uid.isEmpty() || uid.toIntOrNull() != null
    }
    var name by mutableStateOf("")
    var validName by mutableStateOf(false)
    val onNameChange: (String) -> Unit = { name = it
    validName = name.length > 3
    }
    var email by mutableStateOf("")
    var validEmail by mutableStateOf(false)
    val onEmailChange: (String) -> Unit = { email = it
    validEmail = email.contains("@") && email.contains(".")
    }

    fun setUser(user: LocalUser) {
        uid = user.uid?.toString() ?: ""
        name = user.userName
        email = user.email
        validUid = true
        validName = name.length > 3
        validEmail = email.contains("@") && email.contains(".")
    }

    fun toLocalUser(uid:String?=null):LocalUser{
        return LocalUser(uid = uid?.toIntOrNull(), userName = name, email = email)
    }
}