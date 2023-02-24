package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.ToolbarBmiActivity)

        if(supportActionBar != null){ // ye likhna padega agr mujhe apne action bar pe back button chahiye
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // ye action bar display karega
            supportActionBar?.title = "CALCULATE BMI" // ye action bar ka tittle change karega
        }

        binding?.ToolbarBmiActivity?.setNavigationOnClickListener{ // ye mujhe back button de dega mere action baar pe
            onBackPressed()
        }

        makeVisibleMetricUnitView()

        binding?.rgUnits?.setOnCheckedChangeListener {_ , checkedId: Int ->  // for radio groups we can use setOnCheckedChangeListener instead of setOnCLickListener

            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUsUnitView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {

            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear() // height value cleared if it is added
        binding?.etMetricUnitWeight?.text!!.clear()  // weight value cleared if it is added
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUsMetricUnitWeight?.text!!.clear() // height value cleared if it is added
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()  // weight value cleared if it is added
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }


    private fun displayBMiResult(bmi : Float){

        val bmiLabel : String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very Severely underWeight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f)<=0){
            bmiLabel = "Severely underWeight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel = "underWeight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel = "OverWeight"
            bmiDescription = "Oops! You really need to take better care of yourself! WorkOut!"
        }else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel = "Obese Class 1 (Moderately Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! WorkOut!"
        }else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel = "Obese Class 2 (Severely obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! WorkOut!"
        }else{
            bmiLabel = "Obese Class 3 (Very Severely obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! WorkOut!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString() // float to string conversion

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription


    }


    private fun validateMetricUnits():Boolean{ // this function is made to check whether any data is entered or not
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weightValue / (heightValue*heightValue)

                displayBMiResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity,"Please Enter the Value",Toast.LENGTH_SHORT).show()
            }

        }else{
            if(validateUsUnits()){
                val usUnitHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString() //height inch value entered
                val usUnitWeightValue: Float =
                    binding?.etUsMetricUnitWeight?.text.toString().toFloat() // weight value entered in edittext component

                // Here the height feet and inch values are merged and multiplied by 12 for converter

                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMiResult(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please Enter the Value",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUsUnits():Boolean{ // this function is made to check whether any data is entered or not
        var isValid = true

        when{
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() ->{
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() ->{
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() ->{
                isValid = false
            }
        }

        return isValid
    }
}