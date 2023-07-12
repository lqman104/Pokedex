package com.luqman.pokedex.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_pokemon_entity")
data class MyPokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "pokemonId")
    val pokemonId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "custom_name")
    val customName: String,

    @ColumnInfo(name = "url")
    val url: String
)