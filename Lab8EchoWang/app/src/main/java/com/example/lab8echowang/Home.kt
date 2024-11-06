package com.example.lab8echowang

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Colour(val decimal: Long)

@Composable

fun Home(navController: NavController, changeStarColor: (Long) -> Unit) {
    val colors = listOf (
        Colour(4294198070),
        Colour(4293467747),
        Colour(4288423856),
        Colour(4282339765),
        Colour(4280391411),
        Colour(4278228616),
        Colour(4283215696),
        Colour(4294961979)
    )

    LazyColumn {
        items(colors.size) {
            ColorItem(navController, colors[it], changeStarColor)
        }
    }
}

@Composable
fun ColorItem(navController: NavController, color: Colour, changeStarColor: (Long) -> Unit = {}) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .height(120.dp)
        .clickable { changeStarColor(color.decimal) }) {
        Row (modifier = Modifier.fillMaxWidth().background(color= Color(color.decimal)).fillMaxHeight(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically)
        {
            IconButton(
                onClick = {
                    navController.navigate("details/${java.lang.Long.toHexString(color.decimal)}")
                },
            ) {
                Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(40.dp))
            }
        }
    }
}
