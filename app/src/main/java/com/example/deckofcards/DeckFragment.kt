package com.example.deckofcards

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.deckofcards.databinding.FragmentDeckBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckFragment : Fragment() {
    private lateinit var binding: FragmentDeckBinding
    private lateinit var deckId: String
    private val deckViewModel: DeckViewModel by viewModels()
    private lateinit var slideOut: Animation
    private lateinit var slideIn: Animation
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
        slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out)
        setupDeckObserver()
        setupCardObserver()
        deckViewModel.createNewDeck()
        binding.drawCardsButton.setOnClickListener{
            deckViewModel.drawCards(deckId,1)
        }
    }

    private fun setupDeckObserver(){
        deckViewModel.deckResponse.observe(viewLifecycleOwner){result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    val deck = response.body()
                    if (deck!=null){
                        deckId = deck.deck_id
                    }
                } else {
                    handleErrorCode(response.code())
                }
            }
        }
    }
    private fun setupCardObserver(){
        deckViewModel.drawCardResponse.observe(viewLifecycleOwner){result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    val card = response.body()?.cards?.firstOrNull()
                    card?.let {
                        animateCardChange(it.image)
                    }
                } else {
                    handleErrorCode(response.code())
                }
            }
        }
    }
    private fun animateCardChange(imageUrl: String) {

        // Start slide-out animation for the current card
        binding.cardImageView.startAnimation(slideOut)
        slideOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // After slide-out finishes, update the card image
                Glide.with(this@DeckFragment)
                    .load(imageUrl)
                    .into(binding.cardImageView)

                // Delay a tiny bit to allow the layout to process
                binding.cardImageView.post {
                    // Now start the slide-in animation for the new card
                    binding.cardImageView.startAnimation(slideIn)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun handleErrorCode(code: Int) {

    }

}
