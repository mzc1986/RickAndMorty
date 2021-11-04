package com.ds.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ds.rickandmorty.model.Character
import com.ds.rickandmorty.services.CharacterServiceProvider.instanceCharService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterRepository {

    private val allCharacters: MutableLiveData<Character?> = MutableLiveData()
    private val filteredCharacters: MutableLiveData<Character?> = MutableLiveData()

    fun getAllCharacters(page: Int) {
        instanceCharService.getCharacters(page)!!.enqueue(object : Callback<Character?> {
            override fun onResponse(call: Call<Character?>, response: Response<Character?>) {
                allCharacters.postValue(response.body())
            }

            override fun onFailure(call: Call<Character?>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }
        })
    }

    fun searchCharacters(name: String?, status: String?) {
        instanceCharService.searchCharacter(name, status)!!.enqueue(object : Callback<Character?> {
            override fun onResponse(call: Call<Character?>, response: Response<Character?>) {
                filteredCharacters.postValue(response.body())
            }

            override fun onFailure(call: Call<Character?>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }
        })
    }

    val allCharLiveData: LiveData<Character?>
        get() = allCharacters
    val filteredCharLiveData: LiveData<Character?>
        get() = filteredCharacters

    companion object {
        private const val TAG = "char repository"
    }

}