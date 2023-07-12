package com.luqman.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luqman.pokedex.data.database.dao.MyPokemonDao
import com.luqman.pokedex.data.database.entity.MyPokemonEntity

@Database(entities = [MyPokemonEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun myPokemonDao(): MyPokemonDao
}