package com.example.lecture8dtc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

enum class Screen (val route: String){
    HOME("home"),
    INFO("info"),
    MORE("more")
}

data class NavItem(
    val icon: ImageVector,
    val navRoute: String
)

@Composable
fun MyBottomNav(navController: NavController) {
    val navItems = listOf(
        NavItem(Icons.Default.Home, Screen.HOME.route),
        NavItem(Icons.Default.Info, Screen.INFO.route),
        NavItem(Icons.Default.MoreVert, Screen.MORE.route)
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach { item ->
            NavigationBarItem(
                icon = { item.icon },
                selected = currentRoute == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}