package com.example.databaseapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "animalTable")
class Animal(val name: String, val age: String, val breed: String) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}