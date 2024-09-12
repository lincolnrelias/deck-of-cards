package com.example.deckofcards

import com.example.deckofcards.data.apis.DeckApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DeckModule {

    private const val BASE_URL = "https://deckofcardsapi.com/api/deck/"

    @Singleton
    @Provides
    fun provideDeckApiService(okHttpClient: OkHttpClient): DeckApiService {
        return DeckApiService(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideDeckRepository(apiService: DeckApiService): DeckRepository {
        return DeckRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideDeckApi(retrofit: Retrofit): DeckApi {
        return retrofit.create(DeckApi::class.java)
    }
}