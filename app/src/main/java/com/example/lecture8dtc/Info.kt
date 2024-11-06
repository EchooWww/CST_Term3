package com.example.lecture8dtc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Info(){
    Box(modifier = Modifier.fillMaxSize()){
        Text("Info", fontSize = 50.sp, modifier = Modifier.align(Alignment.Center))
    }
}



@Composable
fun More(){
    Box(modifier = Modifier.fillMaxSize()){
        Text("More", fontSize = 50.sp, modifier = Modifier.align(Alignment.Center))
    }
}