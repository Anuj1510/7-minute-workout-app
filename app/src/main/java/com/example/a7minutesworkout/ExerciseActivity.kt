package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConformationBinding
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var ExerciseTimer : CountDownTimer? = null
    private var ExerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExrecisePosition = -1

    private var tts : TextToSpeech? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null



    // aab mene isme binding bana di hai to aab jub bhi mai activity_exercise.xml mai kuch bhi banaunga
    // aur usse ekk id dunga to mujhe usse findviewbyid ki help se call nahi krna hoga mai diectly usse binding
    // ki help se call kr sakta hu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise) // ye mujhe action bar la dega meri activity pe

        if(supportActionBar != null){ // ye likhna padega agr mujhe apne action bar pe back button chahiye
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{ // ye mujhe back button de dega mere action baar pe
            onBackPressed()
        }

        exerciseList = constants.defaultExerciseList()

        tts = TextToSpeech(this,this)



        setupRestView()

        setUpExerciseStatusRecycleView()


    }

    override fun onBackPressed() {
        customDialogForBackButton()
//        super.onBackPressed()
    }

    private fun customDialogForBackButton() { // do exercise ke beech mai ekk pop up aayega, ki whether you want to continue or not
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConformationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYES.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNO.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()

    }


    private fun setUpExerciseStatusRecycleView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTittle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE



        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExrecisePosition + 1].getName()

        setRestProgressBar()

    }

    private fun setupExerciseView(){
        /*
        INVISIBLE:

                This view is invisible, but it still takes up space for layout purposes.

        GONE:

                 This view is invisible, and it doesn't take any space for layout purposes.
         */

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTittle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE



        if(ExerciseTimer != null){
            ExerciseTimer?.cancel()
            ExerciseProgress = 0
        }

        speakOut(exerciseList!![currentExrecisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExrecisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExrecisePosition].getName()

        setExerciseProgressBar()

    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()// we cannot assign number to our text so we convert it into string
            }

            override fun onFinish() {
                currentExrecisePosition++

                exerciseList!![currentExrecisePosition].setisSelected(true) // we are notifing the adapter that the data is changed
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = ExerciseProgress

        ExerciseTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                ExerciseProgress++
                binding?.progressBarExercise?.progress = 10 - ExerciseProgress
                binding?.tvTimerExercise?.text = (10 - ExerciseProgress).toString()// we cannot assign number to our text so we convert it into string
            }

            override fun onFinish() {

                exerciseList!![currentExrecisePosition].setisSelected(false)
                exerciseList!![currentExrecisePosition].setisCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if(currentExrecisePosition<exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,"Congratulations! you have completed the exercises",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ExerciseActivity,EndActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(ExerciseTimer != null){
            ExerciseTimer?.cancel()
            ExerciseProgress = 0
        }

        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTs","The Language specified is not supported")
            }
        }else{
            Log.e("TTS","Initialization Failed!")
        }

    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}