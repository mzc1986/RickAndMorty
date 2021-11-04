package com.ds.rickandmorty.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterServiceProvider {

    private const val BASE_URL = "https://rickandmortyapi.com/api/character/"

    private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var characterService: CharacterService = retrofit.create(CharacterService::class.java)

    @JvmStatic
    val instanceCharService: CharacterService
        get() = characterService

}