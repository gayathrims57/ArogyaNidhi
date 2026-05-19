package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initialize all 5 Quiz Views + District Spinner
        val etIncome = findViewById<EditText>(R.id.etIncome)
        val rgBPL = findViewById<RadioGroup>(R.id.rgBPL)
        val spinnerOccupation = findViewById<Spinner>(R.id.spinnerOccupation)
        val cbDisability = findViewById<CheckBox>(R.id.cbDisability)
        val cbSenior = findViewById<CheckBox>(R.id.cbSenior)
        val spinnerDistrict = findViewById<Spinner>(R.id.spinnerDistrict)
        val btnCheck = findViewById<Button>(R.id.btnCheck)

        // Set up Spinner Adapters (if not set in XML)
        val occAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.occupations_array,
            android.R.layout.simple_spinner_item
        )
        occAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOccupation.adapter = occAdapter

        val distAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.districts_array,
            android.R.layout.simple_spinner_item
        )
        distAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistrict.adapter = distAdapter

        btnCheck.setOnClickListener {
            val incomeStr = etIncome.text.toString()
            val selectedBplId = rgBPL.checkedRadioButtonId
            val occupation = spinnerOccupation.selectedItem.toString()
            val isDisabled = cbDisability.isChecked
            val isSeniorCitizen = cbSenior.isChecked

            if (incomeStr.isEmpty() || selectedBplId == -1) {
                Toast.makeText(
                    this,
                    "Please enter income and select BPL status",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val income = incomeStr.toIntOrNull() ?: 0
            val isBPL = selectedBplId == R.id.rbBPL

            // Decision Tree Logic2
            val result = when {
                (isBPL && income <= 200000) || isDisabled || isSeniorCitizen -> "Eligible for Ayushman Bharat"
                income <= 500000 && occupation == "Farmer" -> "Eligible for State Health Scheme"
                else -> "Not Eligible for Free Schemes"
            }

            val explanation = when (result) {
                "Eligible for Ayushman Bharat" -> "You qualify for ₹5 Lakhs coverage based on family status."
                "Eligible for State Health Scheme" -> "As a Farmer with income under ₹5 Lakhs, you qualify for state care."
                else -> "Income exceeds current thresholds for these specific schemes."
            }

            // Navigate to DetailsActivity
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("RESULT_KEY", result)
            intent.putExtra("EXPLANATION_KEY", explanation)
            intent.putExtra("DISTRICT_KEY", spinnerDistrict.selectedItem.toString())
            startActivity(intent)
        }
    }
}