package com.bcit.lecture8dtc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lecture8dtc.R

@Composable
fun Details(name: String?, image: Int?) {
    Box (Modifier.fillMaxSize()) {
        Column (modifier = Modifier.align(Alignment.Center)) {
            Text(name?:"name", fontSize = 30.sp)
            Image(
                painter = painterResource(image ?: R.drawable.hippo1),
                contentDescription = null,
                Modifier.size(200.dp)
            )
        }
    }

}