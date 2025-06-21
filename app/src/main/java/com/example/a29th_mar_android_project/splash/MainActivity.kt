package com.example.a29th_mar_android_project.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a29th_mar_android_project.R
import com.example.a29th_mar_android_project.continent_detail.ContinentDetailActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        moveToHome();
    }

    //TODO :- Try to add some animation on splash
    private fun moveToHome(){
        startTimer()
    }

    //TODO :- do we need to move this method to view model or anywhere else
    private fun startTimer(){
        // Use Handler and postDelayed for a simple delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start HomeActivity
            val intent = Intent(this, ContinentDetailActivity::class.java)
            startActivity(intent)

            // Optional: Finish MainActivity so the user can't go back to it
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}