package com.personal.cosmeticstock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(::goToMainActivity, DELAY_IN_MILLIS)
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
    }

    companion object {
        const val DELAY_IN_MILLIS = 2000L
    }
}