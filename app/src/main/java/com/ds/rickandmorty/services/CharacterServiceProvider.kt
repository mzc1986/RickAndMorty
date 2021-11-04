package com.ds.rickandmorty.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterServiceProvider {

    private static final String BASE_URL = "https://rickandmortyapi.com/api/character/";
    private static Retrofit retrofit;
    private static CharacterService characterService;
    
    public static CharacterService getInstance(){
        if(characterService == null){
            initializeRetrofit();
            characterService = retrofit.create(CharacterService.class);
        }
        return characterService;
    }
    private static void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
