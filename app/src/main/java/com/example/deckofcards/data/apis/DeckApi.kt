package com.example.deckofcards.data.apis
import com.example.deckofcards.data.models.DeckResponse
import com.example.deckofcards.data.models.DrawCardResponse
import com.example.deckofcards.data.models.DrawFromPileResponse
import com.example.deckofcards.data.models.PileResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckApi {

    // Shuffle a new deck or reshuffle an existing deck
    @GET("new/shuffle/")
    fun shuffleDeck(
            @Query("deck_count") deckCount: Int = 1
    ): Call<DeckResponse>

    // Draw cards from a deck
    @GET("{deck_id}/draw/")
    fun drawCards(
            @Path("deck_id") deckId: String,
            @Query("count") count: Int
    ): Call<DrawCardResponse>

    // Reshuffle an existing deck
    @GET("{deck_id}/shuffle/")
    fun reshuffleDeck(
            @Path("deck_id") deckId: String,
            @Query("remaining") remaining: Boolean = false
    ): Call<DeckResponse>

    // Create a brand new deck
    @GET("new/")
    fun createDeck(
            @Query("jokers_enabled") jokersEnabled: Boolean = false
    ): Call<DeckResponse>

    // Use a partial deck with specific cards
    @GET("new/shuffle/")
    fun createPartialDeck(
            @Query("cards") cards: String
    ): Call<DeckResponse>

    // Add cards to a pile
    @GET("{deck_id}/pile/{pile_name}/add/")
    fun addToPile(
            @Path("deck_id") deckId: String,
            @Path("pile_name") pileName: String,
            @Query("cards") cards: String
    ): Call<PileResponse>

    // Shuffle a pile
    @GET("{deck_id}/pile/{pile_name}/shuffle/")
    fun shufflePile(
            @Path("deck_id") deckId: String,
            @Path("pile_name") pileName: String
    ): Call<PileResponse>

    // List cards in a pile
    @GET("{deck_id}/pile/{pile_name}/list/")
    fun listPile(
            @Path("deck_id") deckId: String,
            @Path("pile_name") pileName: String
    ): Call<PileResponse>

    // Draw cards from a pile
    @GET("{deck_id}/pile/{pile_name}/draw/")
    fun drawFromPile(
            @Path("deck_id") deckId: String,
            @Path("pile_name") pileName: String,
            @Query("cards") cards: String? = null,
            @Query("count") count: Int? = null
    ): Call<DrawFromPileResponse>

    // Return cards to a deck
    @GET("{deck_id}/return/")
    fun returnCardsToDeck(
            @Path("deck_id") deckId: String,
            @Query("cards") cards: String
    ): Call<DeckResponse>
}
