package com.example.lecturedtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.lecturedtc.ui.theme.LectureDTCTheme
import com.example.lecturedtc.ui.state.SignUpState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import com.example.lecturedtc.ui.state.UserState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import com.example.lecturedtc.data.LocalUser


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = (application as MyApp).userRepository
        setContent {
            val usersState = remember { UserState(userRepository) }
            Box(Modifier.safeDrawingPadding()){
                MyForm(usersState)
            }
        }
    }
}

@Composable
fun MyTextField(label:String,value:String, onValueChanged:(String)->Unit, isValid:Boolean){
    TextField(value = value, onValueChange = onValueChanged,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        label= { Text(label) },
        isError = !isValid,
    )
}

@Composable
fun MyForm(userState: UserState) {
    var state = remember { SignUpState() }
    Column(Modifier.fillMaxSize()) {
        Column (Modifier.fillMaxWidth()){
            MyTextField("name",state.name, state.onNameChange, isValid= state.validName)
            MyTextField("email",state.email, state.onEmailChange, isValid= state.validEmail)
        }
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = {
                userState.add(LocalUser(userName = state.name, email = state.email))
            }) {
                Text("Add User")
            }
            Button(onClick = {
                userState.refresh()
            }) {
                Text("Refresh")
            }
        }
        LazyColumn{
            items(userState.users.size){ index ->
                val user = userState.users[index]
                Text("${user.userName} - ${user.email}")
            }
        }    }
}
