package com.example.databaseapp.view

import com.example.databaseapp.model.Animal

interface AnimalListener {

    fun add()

    fun update(animal: Animal)

    fun delete(animal: Animal)
}