package com.hfad.rickandmorty_mvp

import android.app.Application
import androidx.room.Room
import com.hfad.rickandmorty_mvp.room.AppDatabase

class App: Application() {
    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(applicationContext,
        AppDatabase::class.java,
        "characters_database").build()
    }

    fun getDatabase(): AppDatabase{
        return database
    }
}