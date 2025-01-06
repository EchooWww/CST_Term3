package com.example.lecturedtc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lecturedtc.data.Episode
import com.example.lecturedtc.data.EpisodeApi
import kotlinx.coroutines.launch
import com.example.lecturedtc.ui.components.DetailScreen
import com.example.lecturedtc.ui.components.HomeScreen

class MainViewModel : ViewModel() {
    private val episodeApi = EpisodeApi()
    var currentEpisode by mutableStateOf<Episode?>(null)

    fun getRandomEpisode() {
        viewModelScope.launch {
            currentEpisode = episodeApi.getRandomEpisode()
        }
    }
}
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        navController = navController,
                        episode = viewModel.currentEpisode,
                        onRandomClick = {
                            viewModel.getRandomEpisode()
                        }
                    )
                }

                composable("detail/{code}") {
                    DetailScreen(
                        episode = viewModel.currentEpisode,
                        onHomeClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}



