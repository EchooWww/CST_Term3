package com.example.lab8echowang

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavController, starState: Long) {
    CenterAlignedTopAppBar(
        title = { Text("Colors") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null
                )
            }
        },
        actions = {
            Icon(
                Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = Color(starState)
            )
        }
    )
}