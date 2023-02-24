package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    VIEW BINDING -> If view binding is enabled for a module, a binding class is generated for each XML layout file that the
//    module contains.Each binding class contains references to the root view and all views that have an ID.
//    har ekk xml ke liye binding class ban gayi, phir uske baad hame find view by id se call karne ka need nahi
//    hai hum direct bhi kr sakte hai, binding se code easy and short ho jata hai

//    build gradle pe jaake ye likhna hai taake view binding enable ho sake ->
//    buildFeatures{
//        viewBinding true
//    }

    private var binding:ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        // supportActionBar?.hide()  ye action bar ko hide kr deta hai
        //val flStartButton : FrameLayout = findViewById(R.id.flStart) hame findviewby id use nahi krna padha binding
        binding?.flStart?.setOnClickListener{                        // binding ki help se ho gaya
            Toast.makeText(this,"You can start your activity",Toast.LENGTH_LONG).show()

            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent) // agar hum kisi dusri activity mai jaana ho to hum intent ka use krte hai
                                  // kisi button ko click karke dusri activity mai jaana ho tub intent ka use krte hai
        }

        binding?.flBMI?.setOnClickListener{
            Toast.makeText(this,"You can calculate your BMI",Toast.LENGTH_LONG).show()

            val intent = Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener{
            Toast.makeText(this,"Check your History",Toast.LENGTH_LONG).show()

            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}