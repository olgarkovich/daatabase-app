package com.example.databaseapp

import android.app.Application
import com.example.databaseapp.data.AnimalRepository
import com.example.databaseapp.data.AppDatabase
import com.example.databaseapp.model.Animal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AnimalApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { AnimalRepository(database.animalDao()) }

}