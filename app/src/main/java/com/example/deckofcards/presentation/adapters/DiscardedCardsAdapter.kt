package com.example.deckofcards.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deckofcards.R
import com.example.deckofcards.data.models.Card

class DiscardedCardsAdapter(private val discardedCards: MutableList<Card>) :
    RecyclerView.Adapter<DiscardedCardsAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardImageView: ImageView = view.findViewById(R.id.cardImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = discardedCards[position]
        Glide.with(holder.itemView.context)
            .load(card.image)
            .into(holder.cardImageView)
    }

    override fun getItemCount(): Int = discardedCards.size
    fun addCard(card: Card) {
        discardedCards.add(card)
        notifyItemChanged(discardedCards.size-1) // Notify the adapter that a new item is inserted
    }
}