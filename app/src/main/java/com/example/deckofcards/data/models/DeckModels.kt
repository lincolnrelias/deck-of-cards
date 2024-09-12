package com.example.deckofcards.data.models

// Common data class for card
data class Card(
        val code: String,
        val image: String,
        val images: CardImages,
        val value: String,
        val suit: String
)

data class CardImages(
        val svg: String,
        val png: String
)

// Common response for shuffle and deck creation
data class DeckResponse(
        val success: Boolean,
        val deck_id: String,
        val shuffled: Boolean,
        val remaining: Int
)

// Response for drawing cards
data class DrawCardResponse(
        val success: Boolean,
        val deck_id: String,
        val cards: List<Card>,
        val remaining: Int
)

// Response for handling piles
data class PileResponse(
        val success: Boolean,
        val deck_id: String,
        val remaining: Int,
        val piles: Map<String, Pile>
)

data class Pile(
        val remaining: Int,
        val cards: List<Card>? = null
)

// Response for adding to or drawing from piles
data class DrawFromPileResponse(
        val success: Boolean,
        val deck_id: String,
        val remaining: Int,
        val piles: Map<String, Pile>,
        val cards: List<Card>? = null
)
