package com.example.databaseapp.data

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.databaseapp.model.Animal
import android.content.ContentValues

internal const val ANIMAL_NAME = "ANIMAL_NAME"
internal const val ANIMAL_AGE = "ANIMAL_AGE"
internal const val ANIMAL_BREED = "ANIMAL_BREED"

private const val LOG_TAG = "SQLHelper"
private const val DATABASE_NAME = "ANIMAL_DATABASE"
private const val TABLE_NAME = "animal_table"
private const val DATABASE_VERSION = 1
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $ANIMAL_NAME VARCHAR(50), $ANIMAL_AGE VARCHAR(50), $ANIMAL_BREED VARCHAR(50));"

class SQLHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)

        } catch (exception: SQLException) {
            Log.e(LOG_TAG, "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(LOG_TAG, "onUpgrade called")
    }

    fun getCursorWithTopics(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getListOfAnimals(): List<Animal> {
        val listOfAnimals = mutableListOf<Animal>()
        getCursorWithTopics().use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val animalName = cursor.getString(cursor.getColumnIndex(ANIMAL_NAME).toInt())
                    val animalAge = cursor.getString(cursor.getColumnIndex(ANIMAL_AGE).toInt())
                    val animalBreed = cursor.getString(cursor.getColumnIndex(ANIMAL_BREED).toInt())
                    listOfAnimals.add(Animal(animalName, animalAge, animalBreed))
                } while (cursor.moveToNext())
            }
        }
        return listOfAnimals
    }

    fun insert(animal: Animal) {

        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(ANIMAL_NAME, animal.name)
        cv.put(ANIMAL_AGE, animal.age)
        cv.put(ANIMAL_BREED, animal.breed)

        db.insert(TABLE_NAME, null, cv)

        db.close()

    }

    fun update(animal: Animal) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(ANIMAL_NAME, animal.name)
        cv.put(ANIMAL_AGE, animal.age)
        cv.put(ANIMAL_BREED, animal.breed)

        db.update(TABLE_NAME, cv,"id = ?", arrayOf(1.toString()))
    }

    fun delete(animal: Animal) {
        val db = this.writableDatabase

        db.delete(
            TABLE_NAME, "name = ? AND age = ? AND breed = ?",
            arrayOf(animal.name, animal.age, animal.breed))
    }
}