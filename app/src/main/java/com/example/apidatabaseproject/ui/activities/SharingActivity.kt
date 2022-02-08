package com.example.apidatabaseproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.apidatabaseproject.R

class SharingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

        var sharing_text = findViewById(R.id.sharing_message) as TextView
        var shareButton = findViewById(R.id.sharing_button) as Button
        shareButton.setOnClickListener {

            val message = sharing_text.text.toString()
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject_here")
            startActivity(Intent.createChooser(shareIntent, "Share text via"))
        }
    }
}