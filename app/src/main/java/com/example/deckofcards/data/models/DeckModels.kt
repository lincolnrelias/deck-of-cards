package com.example.deckofcards.data.models

data class Card(
        val code: String,
        val image: String,
        val images: CardImages,
        val value: String,
        val suit: String
) {


    // Function to get the numeric rank of the card value
    fun getRank(): Int {
        val valueRanks = mapOf(
            "ACE" to 14,
            "KING" to 13,
            "QUEEN" to 12,
            "JACK" to 11,
            "10" to 10,
            "9" to 9,
            "8" to 8,
            "7" to 7,
            "6" to 6,
            "5" to 5,
            "4" to 4,
            "3" to 3,
            "2" to 2
        )
        return valueRanks[value] ?: 0
    }

    // Compare two cards based on their ranks
    fun compareTo(other: Card): Int {
        return this.getRank().compareTo(other.getRank())
    }

    // Helper function to determine which card is more valuable
    companion object {
        fun getMoreValuableCard(card1: Card, card2: Card): Card {
            return if (card1.compareTo(card2) > 0) card1 else card2
        }
    }
}

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
