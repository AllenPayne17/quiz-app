package com.example.webservicedemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity()  {
    var token: String? = null
    var category = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //remove taskbar
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_result)


        val res = findViewById<View>(R.id.textRes) as TextView
        val cont = findViewById<View>(R.id.rcontinue) as Button
        val cate = findViewById<View>(R.id.rcategory) as Button


        //get score
        val b: Bundle? = intent.extras
        val score = b?.getInt("Score")
        val category = b?.getInt("Category")
        val token = b?.getString("token")

//		que.setText("Question : "+10);
//		res.setText("Score : "+score);
        res.text = "$score/10"

        //"Question: "+qn+"\\"+"10"
    }

    protected override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    fun playAgain(v: View?) {
        val button = findViewById<View>(R.id.rcategory) as Button
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
        button.startAnimation(animation)
        val i = Intent()
        setResult(1, i)
        finish()
    }

    fun endGame(v: View?) {
        val button = findViewById<View>(R.id.rcontinue) as Button
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
        button.startAnimation(animation)
        val j = Intent(this, MainActivity::class.java)
        startActivity(j)
        finish()
    }
}