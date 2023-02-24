package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.a7minutesworkout.databinding.ActivityEndBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_end.*

class EndActivity : AppCompatActivity() {
    private var binding: ActivityEndBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setSupportActionBar(binding?.toolbarExerciseEnd) // ye mujhe action bar la dega meri activity pe

        if(supportActionBar != null){ // ye likhna padega agr mujhe apne action bar pe back button chahiye
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExerciseEnd?.setNavigationOnClickListener{ // ye mujhe back button de dega mere action baar pe
            onBackPressed()
        }

        btn_finish.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}