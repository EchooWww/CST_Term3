package com.example.lecturedtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.lecturedtc.ui.state.SignUpState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import com.example.lecturedtc.ui.state.UserState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lecturedtc.data.LocalUser


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = (application as MyApp).userRepository
        setContent {
            val usersState = remember { UserState(userRepository) }
            Box(Modifier.safeDrawingPadding()) {
                MyForm(usersState)
            }
        }
    }
}

@Composable
fun MyTextField(label: String, value: String, onValueChanged: (String) -> Unit, isValid: Boolean) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = value, onValueChange = onValueChanged,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        label = { Text(label) },
        isError = !isValid,
    )
}

@Composable
fun MyUserItem(user: LocalUser, onDelete: (LocalUser) -> Unit,        onClick: (LocalUser) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(user) },
        shape = RoundedCornerShape(28.dp),
        color = Color(0xE7, 0xE0, 0xEC),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${user.userName}             ${user.email}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black.copy(alpha = 0.8f)
                )
            )

            IconButton(
                onClick = { onDelete(user) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete user",
                    tint = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(modifier= Modifier.padding(8.dp),
        shape = RoundedCornerShape(20.dp),
    onClick = onClick) {
        Text(text)
    }
}

@Composable
fun MyForm(userState: UserState) {
    var state = remember { SignUpState() }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            MyTextField(label = "uid",state.uid,  state.onUidChange ,state.validUid)
            MyTextField("name", state.name, state.onNameChange, isValid = state.validName)
            MyTextField("email", state.email, state.onEmailChange, isValid = state.validEmail)
        }
        Row(
            Modifier.fillMaxWidth()
        , horizontalArrangement = Arrangement.Center)
        {

            MyButton(text = "Add User") {
                userState.add(LocalUser(uid=state.uid.toIntOrNull(),userName = state.name, email = state.email))
            }
            MyButton(text = "Refresh") {
                userState.refresh()

            }
        }
        LazyColumn {
            items(userState.users) { user ->
                MyUserItem(user = user, onDelete = { userState.delete(it) },onClick = { state.setUser(it) })
            }
        }
    }
}
