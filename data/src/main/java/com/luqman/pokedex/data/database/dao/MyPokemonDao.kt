package com.luqman.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.pokedex.data.database.entity.MyPokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyPokemonDao {
    @Query("SELECT * FROM my_pokemon_entity")
    fun getAll(): Flow<List<MyPokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg pokemon: MyPokemonEntity)

    @Query("DELETE FROM my_pokemon_entity WHERE id=:id")
    suspend fun delete(id: Int)
}