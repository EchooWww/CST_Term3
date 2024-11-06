package com.example.lab8echowang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Info(code:String){
    val colorLong = code.toLongOrNull(16) ?: 0xFF000000 // Default to black if the conversion fails
    Box(
        modifier = Modifier.fillMaxSize().background(Color(colorLong)),
        contentAlignment = Alignment.Center
    ) {
        Text("#$code", fontSize = 50.sp)
    }
}