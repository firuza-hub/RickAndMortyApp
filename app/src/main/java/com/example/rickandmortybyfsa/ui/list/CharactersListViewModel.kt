package com.example.rickandmortybyfsa.ui.list

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.rickandmortybyfsa.data.remote.CharactersApiService
import com.example.rickandmortybyfsa.data.remote.CharacterApiStatus
import com.example.rickandmortybyfsa.data.remote.models.CharacterList
import com.example.rickandmortybyfsa.data.remote.retrofit
import kotlinx.coroutines.launch

class CharactersListViewModel(app: Application) : AndroidViewModel(app) {
    private val retrofitService: CharactersApiService by lazy {
        retrofit.create(CharactersApiService::class.java)
    }
    private val _status = MutableLiveData<CharacterApiStatus>()
    val status: LiveData<CharacterApiStatus>
        get() = _status

    var characterList = MutableLiveData<CharacterList>()

    init {
        viewModelScope.launch {

            _status.value = CharacterApiStatus.LOADING
            try {
                characterList.value = retrofitService.getAsync().await()
                _status.value = CharacterApiStatus.DONE
            }
            catch (ex: Exception){
                Log.i("FETCH", "getAsync():" + ex.stackTraceToString())
                _status.value = CharacterApiStatus.ERROR
                Toast.makeText(app, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharactersListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CharactersListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}