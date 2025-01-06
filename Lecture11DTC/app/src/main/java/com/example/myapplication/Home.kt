package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home(artState:ArtState) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello ${artState.name}!")

    }
}