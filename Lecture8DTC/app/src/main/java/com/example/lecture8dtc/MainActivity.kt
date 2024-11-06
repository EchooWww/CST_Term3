package com.bcit.lecture8dtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lecture8dtc.Home
import com.example.lecture8dtc.Info
import com.example.lecture8dtc.MyBottomNav
import com.example.lecture8dtc.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {

        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                MyBottomNav(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.HOME.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.HOME.route) {
                    Home(navController)
                }
                composable(Screen.INFO.route) {
                    Info()
                }
                composable(Screen.MORE.route) {
                    Text("More", modifier = Modifier.fillMaxSize())
                }
                composable(
                    "details/{name}/{image}",
                    arguments = listOf(
                        navArgument("name") { type = NavType.StringType },
                        navArgument("image") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name") ?: ""
                    val image = backStackEntry.arguments?.getInt("image") ?: 0
                    Details(name, image)
                }
            }
        }
    }
