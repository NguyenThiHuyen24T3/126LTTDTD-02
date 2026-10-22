package com.example.eventvalidation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        findViewById<TextView>(R.id.tvName).text =
            intent.getStringExtra("NAME").orEmpty()

        findViewById<TextView>(R.id.tvEmail).text =
            intent.getStringExtra("EMAIL").orEmpty()

        findViewById<TextView>(R.id.tvPhone).text =
            intent.getStringExtra("PHONE").orEmpty()

        findViewById<TextView>(R.id.tvMsv).text =
            intent.getStringExtra("MSV").orEmpty()

        findViewById<TextView>(R.id.tvClass).text =
            intent.getStringExtra("CLASS").orEmpty()

        findViewById<MaterialButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnRegisterAgain)
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                }

                startActivity(intent)
                finish()
            }
    }
}
