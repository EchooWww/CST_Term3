package com.example.lecturedtc.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SignUpState {
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
}