package com.hfad.rickandmorty_mvp.model

import com.google.gson.annotations.SerializedName


data class ResponseCharacters(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val characters: ArrayList<Character>
)

data class Info(
    @SerializedName("count") var count: Int,
    @SerializedName("pages") val pages:Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class Character(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name:String,
    @SerializedName("status") val status:String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String


)

