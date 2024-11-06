package com.example.lecture8dtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @Composable
    fun MainContent() {

        val navController = rememberNavController()

        Scaffold(
            topBar = {
                MyTopBar(navController)
            },
            bottomBar = {
                MyBottomNav(navController)
            }
        ) {
                padding ->
            NavHost(
                navController= navController,
                // The route to the destination
                // So these strings are typically unique
                startDestination = Screen.HOME.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Screen.HOME.route){
                    Home(navController)
                }
                composable(Screen.INFO.route){
                    Info()
                }
                composable(Screen.MORE.route){
                    More()
                }
                composable("details/{name}/{image}",
                    arguments = listOf(navArgument("image"){
                        type = NavType.IntType
                    })){
                    val name = it.arguments?.getString("name")
                    val image = it.arguments?.getInt("image")

                    Details(name, image)
                }


            }
        }



    }
}
