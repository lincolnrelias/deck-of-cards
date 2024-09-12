package com.example.deckofcards.data

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.deckofcards.R

class CardAnimationHelper(private val view: View) {

    fun animateCardChange(imageUrl: String, onEnd: () -> Unit) {
        val slideOut = AnimationUtils.loadAnimation(view.context, R.anim.slide_out)
        val slideIn = AnimationUtils.loadAnimation(view.context, R.anim.slide_in)

        slideOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Glide.with(view.context).load(imageUrl).into(view as ImageView)
                view.post { view.startAnimation(slideIn) }
                onEnd()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view.startAnimation(slideOut)
    }

    fun rotateCard(view: View) {
        val rotateAnimation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        rotateAnimation.duration = 600
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.start()
    }
}
