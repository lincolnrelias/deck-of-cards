package com.example.deckofcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deckofcards.data.models.DeckResponse
import com.example.deckofcards.data.models.DrawCardResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
        private val deckRepository: DeckRepository
) : ViewModel() {

    private val _deckResponse = MutableLiveData<Result<Response<DeckResponse>>?>()
    val deckResponse: MutableLiveData<Result<Response<DeckResponse>>?> = _deckResponse

    private val _drawCardResponse = MutableLiveData<Result<Response<DrawCardResponse>>?>()
    val drawCardResponse: MutableLiveData<Result<Response<DrawCardResponse>>?> = _drawCardResponse

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
}