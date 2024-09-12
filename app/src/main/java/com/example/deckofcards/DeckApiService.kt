package com.example.deckofcards

import com.example.deckofcards.data.apis.DeckApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeckApiService (private val okHttpClient: OkHttpClient) {
    private val baseUrl = "https://deckofcardsapi.com/api/deck/"
    private var retrofit: DeckApi? = null
    fun createApi(): DeckApi {
        if (retrofit != null) return retrofit as DeckApi
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DeckApi::class.java)
        return retrofit as DeckApi
    }
}