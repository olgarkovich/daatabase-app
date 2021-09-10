package com.example.databaseapp.data

import androidx.room.*
import com.example.databaseapp.model.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {

    @Insert
    suspend fun insert(animal: Animal)

    @Update
    suspend fun update(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)

    @Query("SELECT * FROM animalTable ")
    fun loadAllAnimals(): Flow<List<Animal>>

}
