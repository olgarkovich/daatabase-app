package com.example.databaseapp.view.main

import androidx.lifecycle.*
import com.example.databaseapp.data.AnimalRepository
import com.example.databaseapp.model.Animal
import kotlinx.coroutines.launch

class AnimalsViewModel(private val repository: AnimalRepository): ViewModel() {

    val allAnimals: LiveData<List<Animal>> = repository.allAnimals.asLiveData()

    fun insert(animal: Animal) = viewModelScope.launch {
        repository.insert(animal)
    }

    fun update(animal: Animal) = viewModelScope.launch {
        repository.update(animal)
    }

    fun delete(animal: Animal) = viewModelScope.launch {
        repository.delete(animal)
    }
}

class AnimalViewModelFactory(private val repository: AnimalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimalsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}