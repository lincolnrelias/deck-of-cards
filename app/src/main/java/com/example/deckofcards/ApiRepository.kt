package com.example.deckofcards

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ApiRepository {
    open fun <T> makeApiCall(
            call: Call<T>,
            callback: (Result<Response<T>>) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback(Result.success(response))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}