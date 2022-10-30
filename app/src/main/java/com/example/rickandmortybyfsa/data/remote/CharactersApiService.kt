package com.example.rickandmortybyfsa.data.remote

import com.example.rickandmortybyfsa.data.remote.models.CharacterDetails
import com.example.rickandmortybyfsa.data.remote.models.CharacterList
import com.example.rickandmortybyfsa.utils.API_URL_BASE
import com.example.rickandmortybyfsa.utils.API_URL_CHARACTERS
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface CharactersApiService {
    @GET(API_URL_CHARACTERS)
    fun getAsync(@Query("page") currentPage:Int): Deferred<CharacterList>

    @GET(API_URL_CHARACTERS)
    fun getByNameAsync(@Query("name") keyword:String): Deferred<CharacterList>

}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(API_URL_BASE)
    .build()


enum class CharacterApiStatus { LOADING, ERROR, DONE }