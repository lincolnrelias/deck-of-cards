package com.example.deckofcards.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deckofcards.data.repositories.DeckRepository
import com.example.deckofcards.data.models.Card
import com.example.deckofcards.data.models.DeckResponse
import com.example.deckofcards.data.models.DrawCardResponse
import com.example.deckofcards.data.models.PileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
        private val deckRepository: DeckRepository
) : ViewModel() {

    //Livedata
    private val _deckResponse = MutableLiveData<Result<Response<DeckResponse>>?>()
    val deckResponse: MutableLiveData<Result<Response<DeckResponse>>?> = _deckResponse

    private val _drawCardResponse = MutableLiveData<Result<Response<DrawCardResponse>>?>()
    val drawCardResponse: MutableLiveData<Result<Response<DrawCardResponse>>?> = _drawCardResponse

    private val _discardResponse = MutableLiveData<Result<Response<PileResponse>>?>()
    val discardResponse: MutableLiveData<Result<Response<PileResponse>>?> = _discardResponse

    private val _pileResponse = MutableLiveData<Result<Response<PileResponse>>?>()
    val pileResponse: MutableLiveData<Result<Response<PileResponse>>?> = _pileResponse

    fun createNewDeck() {
        deckRepository.createNewDeck { result ->
            _deckResponse.postValue(result)
        }
    }

    fun drawCards(deckId: String, count: Int) {
        deckRepository.drawCard(deckId, count) { result ->
            _drawCardResponse.postValue(result)
        }
    }

    fun shuffleDeck(deckId: String){
        deckRepository.reshuffleDeck(deckId,true){result ->
            _deckResponse.postValue(result)
        }
    }

    fun discardCard(deckId: String,pileName: String,card: Card){
        deckRepository.addToPile(deckId,pileName,card.code){result ->
            _discardResponse.postValue(result)
        }
    }

    fun getDiscardedPile(deckId: String,pileName: String){
        deckRepository.listPile(deckId,pileName){ result ->
            _pileResponse.postValue(result)
        }
    }

}