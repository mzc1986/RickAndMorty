package com.ds.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ds.rickandmorty.model.Character
import com.ds.rickandmorty.repository.CharacterRepository

class MainActivityViewModel : ViewModel() {

    private val characterRepository: CharacterRepository = CharacterRepository()

    fun getPages(i: Int) =
        characterRepository.getAllCharacters(i)

    fun searchCharacters(name: String?, status: String?) =
        characterRepository.searchCharacters(name, status)

    val allCharactersLiveData: LiveData<Character?>
        get() = characterRepository.allCharLiveData

    val filteredCharLiveData: LiveData<Character?>
        get() = characterRepository.filteredCharLiveData

    init {
        getPages(1)
    }
}