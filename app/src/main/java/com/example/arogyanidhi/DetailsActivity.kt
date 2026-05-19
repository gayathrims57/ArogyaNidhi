package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // 1. Receive data from the Quiz (MainActivity)
        val result = intent.getStringExtra("RESULT_KEY") ?: "Not Eligible"
        val explanation = intent.getStringExtra("EXPLANATION_KEY") ?: ""
        val district = intent.getStringExtra("DISTRICT_KEY") ?: "Bangalore"

        // 2. Map UI Elements - ensure these IDs exist in activity_details.xml
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvExplanation = findViewById<TextView>(R.id.tvExplanation)
        val tvDocList = findViewById<TextView>(R.id.tvDocList)
        val tvHospitalList = findViewById<TextView>(R.id.tvHospitalList)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // 3. Set basic result text
        tvResult.text = result
        tvExplanation.text = explanation

        // 4. Dynamic Document Checklist logic
        val docs = when (result) {
            "Eligible for Ayushman Bharat" -> "• [Aadhaar Redacted]\n• BPL Card\n• Ration Card"
            "Eligible for State Health Scheme" -> "• [Aadhaar Redacted]\n• Income Certificate\n• Address Proof"
            else -> "• [Aadhaar Redacted]\n• General ID Proof"
        }
        tvDocList.text = "Required Documents:\n$docs"

        // 5. Dynamic Hospital List (Searchable by District Requirement)
        val hospitals = when (district) {
            "Bangalore" -> "• Victoria Hospital\n• Bowring Hospital\n• KC General Hospital"
            "Mysore" -> "• K.R. Hospital\n• Cheluvamba Hospital\n• JSS Hospital"
            "Hubli" -> "• KIMS Hospital\n• Tatwadarsha Hospital"
            "Mangalore" -> "• Wenlock District Hospital\n• AJ Hospital"
            else -> "• Visit nearest Government Hospital"
        }
        tvHospitalList.text = "Hospitals in $district:\n$hospitals"

        // 6. Share Button Logic
        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareMessage = "Arogya-Nidhi Result:\n$result\n\nRequired Documents:\n$docs\n\nHospitals in $district:\n$hospitals"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share Document Checklist"))
        }

        // 7. Back Button Logic
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    } // This was the missing closing brace!
}
