package com.ds.rickandmorty.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ds.rickandmorty.model.Character;
import com.ds.rickandmorty.services.CharacterServiceProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {

    private static final String TAG = "char repository";
    private MutableLiveData<Character> allCharacters;
    private MutableLiveData<Character> filteredCharacters;

    public CharacterRepository() {
        allCharacters = new MutableLiveData<>();
        filteredCharacters = new MutableLiveData<>();
    }

    public void getAllCharacters(int page){
        CharacterServiceProvider.getInstance().getCharacters(page).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                allCharacters.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void searchCharacters(String name, String status){
        CharacterServiceProvider.getInstance().searchCharacter(name, status).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                filteredCharacters.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<Character> getAllCharLiveData(){
        return allCharacters;
    }

    public LiveData<Character> getFilteredCharLiveData(){
        return filteredCharacters;
    }
}
