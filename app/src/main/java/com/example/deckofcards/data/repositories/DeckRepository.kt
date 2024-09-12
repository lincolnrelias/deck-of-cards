package com.example.deckofcards.data.repositories

import com.example.deckofcards.data.apis.DeckApi
import com.example.deckofcards.data.models.DeckResponse
import com.example.deckofcards.data.models.DrawCardResponse
import com.example.deckofcards.data.models.DrawFromPileResponse
import com.example.deckofcards.data.models.PileResponse
import retrofit2.Response

class DeckRepository(
        private val apiService: DeckApi,
) : ApiRepository() {

    // Draw a card
    fun drawCard(deckId: String, count: Int = 1, callback: (Result<Response<DrawCardResponse>>) -> Unit) {
        val call = apiService.drawCards(deckId, count)
        makeApiCall(call, callback)
    }

    // Shuffle a new deck
    fun shuffleDeck(deckCount: Int = 1, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.shuffleDeck(deckCount)
        makeApiCall(call, callback)
    }

    // Reshuffle an existing deck
    fun reshuffleDeck(deckId: String, remaining: Boolean = false, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.reshuffleDeck(deckId, remaining)
        makeApiCall(call, callback)
    }

    // Create a brand new deck
    fun createNewDeck(jokersEnabled: Boolean = false, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createDeck(jokersEnabled)
        makeApiCall(call, callback)
    }

    // Create a partial deck with specific cards
    fun createPartialDeck(cards: String, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.createPartialDeck(cards)
        makeApiCall(call, callback)
    }

    // Add cards to a pile
    fun addToPile(deckId: String, pileName: String, cards: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.addToPile(deckId, pileName, cards)
        makeApiCall(call, callback)
    }

    // Shuffle a pile
    fun shufflePile(deckId: String, pileName: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.shufflePile(deckId, pileName)
        makeApiCall(call, callback)
    }

    // List cards in a pile
    fun listPile(deckId: String, pileName: String, callback: (Result<Response<PileResponse>>) -> Unit) {
        val call = apiService.listPile(deckId, pileName)
        makeApiCall(call, callback)
    }

    // Draw cards from a pile
    fun drawFromPile(deckId: String, pileName: String, cards: String? = null, count: Int? = null, callback: (Result<Response<DrawFromPileResponse>>) -> Unit) {
        val call = apiService.drawFromPile(deckId, pileName, cards, count)
        makeApiCall(call, callback)
    }

    // Return cards to a deck
    fun returnCardsToDeck(deckId: String, cards: String, callback: (Result<Response<DeckResponse>>) -> Unit) {
        val call = apiService.returnCardsToDeck(deckId, cards)
        makeApiCall(call, callback)
    }
}