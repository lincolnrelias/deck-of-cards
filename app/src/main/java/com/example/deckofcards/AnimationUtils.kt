package com.example.deckofcards

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class AnimationUtils(context: Context) {
    var slideIn: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in)
    var slideOut:  Animation = AnimationUtils.loadAnimation(context, R.anim.slide_out)
}