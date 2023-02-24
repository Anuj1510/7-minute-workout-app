package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        iv_note.alpha = 0f ---> splash screen without Animation
//        iv_note.animate().setDuration(1500).alpha(1f).withEndAction{
//            val i = Intent(this,MainActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//            finish()
//        }

        val backgrounding : ImageView = findViewById(R.id.iv_note) // splash screen with animation
        val slideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide)
        backgrounding.startAnimation(slideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },6000)
    }
}