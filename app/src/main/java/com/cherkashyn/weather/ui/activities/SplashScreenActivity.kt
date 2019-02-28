package com.cherkashyn.weather.ui.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cherkashyn.weather.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideStatusBar()
        initAnimatorListener(Intent(this@SplashScreenActivity, MainActivity::class.java))
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun initAnimatorListener(intent: Intent) {
        splashAnimationViewLogo.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                startActivity(intent)
            }
        })
    }
}