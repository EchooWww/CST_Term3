package com.example.lecture8dtc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Info() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Info",fontSize = 30.sp)
    }
}