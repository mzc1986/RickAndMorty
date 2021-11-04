package com.ds.rickandmorty.services;

import com.ds.rickandmorty.model.Character;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharacterService {
    @GET(".")
    Call<Character> getCharacters(
            @Query("page") int page
    );

    @GET(".")
    Call<Character> searchCharacter(
            @Query("name") String name,
            @Query("status") String status
    );
}
