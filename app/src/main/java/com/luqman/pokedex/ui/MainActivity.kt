package com.luqman.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.luqman.pokedex.ui.Destination.DETAIL_WITH_PARAMETER
import com.luqman.pokedex.ui.Destination.MAIN_MENU
import com.luqman.pokedex.ui.Destination.NAME_PARAMETER
import com.luqman.pokedex.ui.detail.DetailScreen
import com.luqman.pokedex.ui.menu.MenuScreen
import com.luqman.pokedex.uikit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = MAIN_MENU) {
        composable(MAIN_MENU) {
            MenuScreen(mainNavController = navigationController, modifier = Modifier.fillMaxSize())
        }
        composable(
            route = DETAIL_WITH_PARAMETER,
            arguments = listOf(navArgument(NAME_PARAMETER) { type = NavType.StringType })
        ) {
            DetailScreen(
                navHostController = navigationController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

object Destination {
    const val NAME_PARAMETER = "name"

    const val MAIN_MENU = "main_menu"
    const val DETAIL = "detail/"
    const val DETAIL_WITH_PARAMETER = "$DETAIL{$NAME_PARAMETER}"
}