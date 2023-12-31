package com.example.newsapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startCategoriesActivity()
    }

    private fun startCategoriesActivity() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }, 2000)
    }
}