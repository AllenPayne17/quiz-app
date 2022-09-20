package com.example.webservicedemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun play(view: View?) {
        val i = Intent(this, PlayActivity::class.java)
        startActivity(i)
        finish()
    }

    fun exit(view: View?) {
        finish()
        System.exit(0)
    }

    fun about(view: View?) {
        val j = Intent(this, AboutActivity::class.java)
        startActivity(j)
        finish()

    }

}