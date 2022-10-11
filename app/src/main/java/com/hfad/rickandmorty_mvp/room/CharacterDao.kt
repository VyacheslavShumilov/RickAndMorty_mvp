package com.hfad.rickandmorty_mvp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hfad.rickandmorty_mvp.room.Model.Characters

@Dao
interface CharacterDao {

    @Insert
    fun insertCharacter(character: Characters)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<Characters>

}