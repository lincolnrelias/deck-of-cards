package com.example.deckofcards

import com.example.deckofcards.data.models.*
import retrofit2.Response

class DeckRepository(
        private val apiService: DeckApiService,
) : ApiRepository() {

    // Draw a card
    fun drawCard(deckId: String, count: Int = 1, callback: (Result<Response<DrawCardResponse>>) -> Unit) {
        val call = apiService.createApi().drawCards(deckId, count)
        makeApiCall(call, callback)
    }

    // Shuffle a new deck
    fun shuffleDeck(deckCount: Int = 1, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createApi().shuffleDeck(deckCount)
        makeApiCall(call, callback)
    }

    // Reshuffle an existing deck
    fun reshuffleDeck(deckId: String, remaining: Boolean = false, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createApi().reshuffleDeck(deckId, remaining)
        makeApiCall(call, callback)
    }

    // Create a brand new deck
    fun createNewDeck(jokersEnabled: Boolean = false, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createApi().createDeck(jokersEnabled)
        makeApiCall(call, callback)
    }

    // Create a partial deck with specific cards
    fun createPartialDeck(cards: String, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createApi().createPartialDeck(cards)
        makeApiCall(call, callback)
    }

    // Add cards to a pile
    fun addToPile(deckId: String, pileName: String, cards: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.createApi().addToPile(deckId, pileName, cards)
        makeApiCall(call, callback)
    }

    // Shuffle a pile
    fun shufflePile(deckId: String, pileName: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.createApi().shufflePile(deckId, pileName)
        makeApiCall(call, callback)
    }

    // List cards in a pile
    fun listPile(deckId: String, pileName: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.createApi().listPile(deckId, pileName)
        makeApiCall(call, callback)
    }

    // Draw cards from a pile
    fun drawFromPile(deckId: String, pileName: String, cards: String? = null, count: Int? = null, callback: (Result<Response<DrawFromPileResponse>>) -> Unit) {
        val call = apiService.createApi().drawFromPile(deckId, pileName, cards, count)
        makeApiCall(call, callback)
    }

    // Return cards to a deck
    fun returnCardsToDeck(deckId: String, cards: String, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createApi().returnCardsToDeck(deckId, cards)
        makeApiCall(call, callback)
    }
}