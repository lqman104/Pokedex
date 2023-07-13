package com.luqman.pokedex.ui.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luqman.pokedex.ui.Destination.DETAIL
import com.luqman.pokedex.ui.list.ListScreen
import com.luqman.pokedex.ui.mypokemon.MyPokemonListScreen

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            MainBottomBar(navController)
        }
    ) { padding: PaddingValues ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navController = navController,
            startDestination = MainScreenMenu.Pokedex.route,
        ) {
            composable(MainScreenMenu.Pokedex.route) {
                ListScreen(
                    snackbarHostState = snackbarHostState,
                    onItemClicked = { name ->
                        mainNavController.navigate(DETAIL + name)
                    }
                )
            }
            composable(MainScreenMenu.MyPokemon.route) {
                MyPokemonListScreen(
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

@Composable
fun MainBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        windowInsets = WindowInsets(bottom = 0.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        MainScreenMenu.getMainScreenMenus().forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}