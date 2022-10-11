package com.hfad.rickandmorty_mvp.services

import com.hfad.rickandmorty_mvp.model.Character
import com.hfad.rickandmorty_mvp.model.ResponseCharacters
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("character")
    fun getCharacter(@Query("page") page: Int): Call<ResponseCharacters>

    @GET("character/{id}")
    fun getCharacterInfo(@Path("id") id: Int): Call<Character>

    companion object {
        var BASE_URL = "https://rickandmortyapi.com/api/"
        fun create(): Api {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(Api::class.java)
        }
    }


}