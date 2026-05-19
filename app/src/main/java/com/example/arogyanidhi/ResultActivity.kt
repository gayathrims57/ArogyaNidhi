package com.example.arogyanidhi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Using the exact keys from MainActivity
        val result = intent.getStringExtra("RESULT_KEY") ?: "No Result"
        val explanation = intent.getStringExtra("EXPLANATION_KEY") ?: "No Explanation"

        findViewById<TextView>(R.id.tvResultTitle).text = result
        findViewById<TextView>(R.id.tvExplanation).text = explanation

        findViewById<Button>(R.id.btnViewDetails).setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
        }
    }
}