package com.example.lecture8dtc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavController){
    MediumTopAppBar(
        title = { Text("Hippo Zoo") },
        navigationIcon = {
            IconButton(
                onClick= {
                    navController.popBackStack()
                }
            ){
                Icon(Icons.Default.ArrowBack, contentDescription = null)

            }
        },
        actions = {
            IconButton(
                onClick= {
                    navController.navigate(Screen.HOME.route)
                }
            ){
                Icon(Icons.Default.Home, contentDescription = null)

            }

            IconButton(
                onClick= {
                    navController.navigate(Screen.INFO.route)
                }
            ){
                Icon(Icons.Default.Info, contentDescription = null)

            }

            IconButton(
                onClick= {
                    navController.navigate(Screen.MORE.route)
                }
            ){
                Icon(Icons.Default.MoreVert, contentDescription = null)

            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    )
}