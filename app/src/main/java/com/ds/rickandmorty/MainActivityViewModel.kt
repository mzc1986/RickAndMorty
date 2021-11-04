package com.ds.rickandmorty;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ds.rickandmorty.model.Character;
import com.ds.rickandmorty.model.Result;
import com.ds.rickandmorty.repository.CharacterRepository;

import java.util.List;


public class MainActivityViewModel extends ViewModel {

    private static final String TAG = "retrofit";

    private CharacterRepository characterRepository;

    public MainActivityViewModel() {
        super();
        characterRepository = new CharacterRepository();
        getPages(1);
    }

    public void getPages(int i){
        characterRepository.getAllCharacters(i);
    }
    public void searchCharacters(String name, String status){
        characterRepository.searchCharacters(name, status);
    }

    public LiveData<Character> getAllCharactersLiveData(){
        return characterRepository.getAllCharLiveData();
    }

    public LiveData<Character> getFilteredCharLiveData(){
        return characterRepository.getFilteredCharLiveData();
    }
}
