package com.example.databaseapp.data

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.databaseapp.model.Animal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class AnimalRepository(private val animalDao: AnimalDao) {

    val allAnimals: Flow<List<Animal>> = animalDao.loadAllAnimals()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(animal: Animal) {
        animalDao.insert(animal)
    }
//    val allAnimals: List<Animal> = animalDao.loadAllAnimals()
//
//    fun loadAll(animals: ArrayList<Animal>) {
//        animals.addAll(animalDao.loadAllAnimals())
//    }
//    suspend fun insert(animal: Animal) {
//        animalDao.insert(animal)
//    }

//    suspend fun update(animal: Animal) {
//        animalDao.update(animal)
//    }
//
//    suspend fun delete(animal: Animal) {
//        animalDao.delete()
//    }
}
