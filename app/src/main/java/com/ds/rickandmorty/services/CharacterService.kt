package com.ds.rickandmorty.services

import com.ds.rickandmorty.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET(".")
    fun getCharacters(
            @Query("page") page: Int
    ): Call<Character?>?

    @GET(".")
    fun searchCharacter(
            @Query("name") name: String?,
            @Query("status") status: String?
    ): Call<Character?>?
}