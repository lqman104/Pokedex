package com.luqman.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.luqman.pokedex.data.database.entity.MyPokemonEntity

@Dao
interface MyPokemonDao {
    @Query("SELECT * FROM my_pokemon_entity")
    fun getAll(): List<MyPokemonEntity>

    @Upsert
    fun insert(vararg pokemon: MyPokemonEntity)

    @Query("DELETE FROM my_pokemon_entity WHERE id=:id")
    fun delete(id: Int)
}