package com.cahyana.asep.moviecatalogue.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.cahyana.asep.moviecatalogue.R
import com.cahyana.asep.moviecatalogue.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private val timeWaitInMillsec = 2000L;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentHome = Intent(this, HomeActivity::class.java)
            startActivity(intentHome)
            finish()
        }, timeWaitInMillsec)
    }
}