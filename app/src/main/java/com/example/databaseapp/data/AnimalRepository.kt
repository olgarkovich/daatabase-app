package com.example.databaseapp.data

import androidx.annotation.WorkerThread
import com.example.databaseapp.model.Animal
import kotlinx.coroutines.flow.Flow

class AnimalRepository(private val animalDao: AnimalDao) {

    val allAnimals: Flow<List<Animal>> = animalDao.loadAllAnimals()

    @WorkerThread
    suspend fun insert(animal: Animal) {
        animalDao.insert(animal)
    }

    @WorkerThread
    suspend fun update(animal: Animal) {
        animalDao.update(animal)
    }

    @WorkerThread
    suspend fun delete(animal: Animal) {
        animalDao.delete(animal)
    }
}
