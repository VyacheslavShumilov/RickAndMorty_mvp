package com.hfad.rickandmorty_mvp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.rickandmorty_mvp.room.Model.Characters

@Database(entities = [Characters::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharacterDao
}