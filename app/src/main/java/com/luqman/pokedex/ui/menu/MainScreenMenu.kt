package com.luqman.pokedex.ui.menu

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.luqman.pokedex.R

sealed class MainScreenMenu(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Pokedex : MainScreenMenu(
        DESTINATION_POKEDEX, R.string.pokedex_menu, Icons.Default.Search
    )
    object MyPokemon : MainScreenMenu(
        DESTINATION_MY_POKEMON, R.string.my_pokemon_menu, Icons.Default.Favorite
    )

    companion object {
        private const val DESTINATION_POKEDEX = "POKEDEX"
        private const val DESTINATION_MY_POKEMON = "MY_POKEMON"

        fun getMainScreenMenus(): List<MainScreenMenu> {
            return listOf(Pokedex, MyPokemon)
        }
    }
}