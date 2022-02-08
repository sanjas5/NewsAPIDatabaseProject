package com.example.apidatabaseproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.apidatabaseproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var skip_button = findViewById(R.id.nextbutton) as Button
        skip_button.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent);
        }
    }


}