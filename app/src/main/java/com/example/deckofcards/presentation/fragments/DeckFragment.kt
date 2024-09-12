package com.example.deckofcards.presentation.fragments

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.deckofcards.domain.viewmodels.DeckViewModel
import com.example.deckofcards.presentation.adapters.DiscardedCardsAdapter
import com.example.deckofcards.R
import com.example.deckofcards.data.models.Card
import com.example.deckofcards.databinding.FragmentDeckBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckFragment : Fragment() {
    private lateinit var binding: FragmentDeckBinding
    private lateinit var deckId: String
    private val deckViewModel: DeckViewModel by viewModels()
    private lateinit var slideOut: Animation
    private lateinit var slideIn: Animation
    private var card:Card? = null
    private var lastCard:Card? = null
    var startGame = true
    var score = 0
    var guess = 1
    val discardedCards = mutableListOf<Card>()
    private lateinit var discardedCardsAdapter: DiscardedCardsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_deck, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAnimations()
        setupObservers()
        setupButtons()
        discardedCardsAdapter = DiscardedCardsAdapter(discardedCards)
        binding.discardedCardsRecyclerView.adapter = discardedCardsAdapter
        deckViewModel.createNewDeck()
        showStartPopup()
    }

    private fun initializeAnimations() {
        slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out)
        slideIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.drawCardsButton.isEnabled = true
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun setupButtons() {
        binding.drawCardsButton.setOnClickListener {
            deckViewModel.drawCards(deckId, 1)
            binding.drawCardsButton.isEnabled = false
        }
        binding.ivReset.setOnClickListener{
            restartGame()
        }
        binding.shuffleButton.setOnClickListener {
            deckViewModel.shuffleDeck(deckId)
        }
        binding.ivLess.setOnClickListener {
            guess = -1;
            binding.tvNextCard.text = "Menor!"
        }
        binding.ivEqual.setOnClickListener {
            guess = 0
            binding.tvNextCard.text = "Igual!"
        }
        binding.ivMore.setOnClickListener {
            guess = 1
            binding.tvNextCard.text = "Maior!"
        }
        binding.shuffleButton.setOnClickListener {
            deckViewModel.shuffleDeck(deckId)
            val rotateAnimation = ObjectAnimator.ofFloat(binding.flatCardImage, "rotation", 0f, 360f)
            rotateAnimation.duration = 600
            rotateAnimation.interpolator = LinearInterpolator()
            rotateAnimation.start()
        }
    }

    private fun setupObservers(){
        deckViewModel.deckResponse.observe(viewLifecycleOwner){result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    val deck = response.body()
                    if (deck!=null){
                        deckId = deck.deck_id
                        if(!deck.shuffled){
                            deckViewModel.shuffleDeck(deckId)
                        }else if (startGame){
                            deckViewModel.drawCards(deckId,1)
                            startGame = false
                        }
                    }
                } else {
                    handleErrorCode(response.code())
                }
            }
        }
        deckViewModel.discardResponse.observe(viewLifecycleOwner){result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    deckViewModel.getDiscardedPile(deckId,"discard")
                } else {
                    handleErrorCode(response.code())
                }
            }
        }
        deckViewModel.pileResponse.observe(viewLifecycleOwner){result->
            result?.onSuccess { response ->
                if (response.isSuccessful){
                    val cardsList = response.body()?.piles?.get("discard")?.cards
                    if (cardsList != null) {
                        discardedCardsAdapter.addCard(cardsList.last())
                    }
                }else{
                    handleErrorCode(response.code())
                }
            }
        }
        deckViewModel.drawCardResponse.observe(viewLifecycleOwner) { result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    val deck = response.body()
                    val newCard = deck?.cards?.firstOrNull()
                    if (newCard != null) {
                        card?.let {
                            checkCardGuess(newCard)
                        }
                        card = newCard
                        animateCardChange(newCard.image)
                    }
                    if (lastCard == null) {
                        lastCard = card
                    }
                    if (deck?.remaining == 0) {
                        showEndGamePopup()
                    }
                } else {
                    handleErrorCode(response.code())
                }
            }
        }
    }

    private fun checkCardGuess(newCard: Card) {
        val diff = newCard.compareTo(card!!)
        if (diff > 0) {
            score += if (guess == 1) 100 else 0
        } else if (diff == 0) {
            score += if (guess == 0) 400 else 0
        } else {
            score += if (guess == -1) 100 else 0
        }
        binding.scoreTextView.text = "Score: $score"
    }

    private fun showEndGamePopup() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Parabéns!")
            .setMessage("Jogo encerrado, seu score é: $score")
            .setPositiveButton("Reiniciar") { dialog, _ ->
                restartGame()
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun showStartPopup() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Olá! este jogo é bem simples!")
            .setMessage("Tente adivinhar se a próxima carta é maior, menor ou igual a atual!")
            .setPositiveButton("Começar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun animateCardChange(imageUrl: String) {

        // don't play animation when no card has been drawn
        if(binding.cardImageView.visibility==View.INVISIBLE){
            binding.cardImageView.visibility = View.VISIBLE
            binding.cardImageView.apply {
                Glide.with(this).load(imageUrl).into(this)
                binding.cardImageView.post {
                    // Now start the slide-in animation for the new card
                    binding.cardImageView.startAnimation(slideIn)
                }
            }.also { return }
        }
        slideOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                lastCard?.let { deckViewModel.discardCard(deckId, "discard", it) }
                lastCard = card
                Glide.with(this@DeckFragment)
                    .load(imageUrl)
                    .into(binding.cardImageView)

                // Delay a tiny bit to allow the layout to process
                binding.cardImageView.post {
                    binding.cardImageView.startAnimation(slideIn)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        binding.cardImageView.startAnimation(slideOut)
    }

    private fun restartGame() {
        // Reset score and deck related
        score = 0
        card = null
        lastCard = null
        discardedCards.clear()
        discardedCardsAdapter.notifyDataSetChanged()
        startGame = true

        // Reset UI
        binding.scoreTextView.text = "Score: 0"
        binding.cardImageView.visibility = View.INVISIBLE
        binding.drawCardsButton.isEnabled = true
        binding.tvNextCard.text = ""

        // Create new deck
        deckViewModel.createNewDeck()
        binding.root.showSnackbar("Jogo reiniciado, boa sorte!")
    }

    private fun View.showSnackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleErrorCode(code: Int) {
        when (code) {
            400 -> binding.root.showSnackbar("Bad request. Please try again.")
            401 -> binding.root.showSnackbar("Unauthorized access. Please check your API key.")
            404 -> binding.root.showSnackbar("Deck not found. Restarting game.")
            500 -> binding.root.showSnackbar("Server error. Please try again later.")
            else -> binding.root.showSnackbar("Unknown error occurred.")
        }
    }

}
