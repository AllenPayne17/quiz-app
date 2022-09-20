package com.example.webservicedemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class SplashActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //remove taskbar

        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({ goToMainScreen() }, 3400)
    }

    private fun goToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        exitProcess(0)
    }
}