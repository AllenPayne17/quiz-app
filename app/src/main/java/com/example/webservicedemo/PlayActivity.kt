package com.example.webservicedemo

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PlayActivity : AppCompatActivity()  {
    private var url: String? = null
    private var score: Int = 0
    private var qid: Int = 0
    private var qcount: Int = 0
    private val cat = 9
    private var qn = 1
    private var timeValue = 15
    private var token: String? = null
    private var currentQ: QuestionClass? = null
    private var cansQ:QuestionClass? = null
    private var qtype = true
    private var question = ArrayList<QuestionClass>()
    private var cquestion = ArrayList<QuestionClass>()


    private lateinit var txtQuestion : TextView
    private lateinit var tvScore : TextView
    private lateinit var tvqnum : TextView
    private lateinit var opt1: TextView
    private lateinit var opt2 : TextView
    private lateinit var opt3 : TextView
    private lateinit var opt4 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        opt1 = findViewById(R.id.btnOptionA)
        opt2 = findViewById(R.id.btnOptionB)
        opt3 = findViewById(R.id.btnOptionC)
        opt4 = findViewById(R.id.btnOptionD)
        tvScore =findViewById(R.id.tvScore)
        txtQuestion =findViewById(R.id.tvQuestion)
        tvqnum = findViewById(R.id.tvqnum)

        loadQ()
    }

    fun clickOption1(v: View?) {
        handleButtonClick(1)
        val button = findViewById<View>(R.id.btnOptionA) as TextView
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        button.startAnimation(animation)
    }

    fun clickOption2(v: View?) {
        handleButtonClick(2)
        val button = findViewById<View>(R.id.btnOptionB) as TextView
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        button.startAnimation(animation)
    }

    fun clickOption3(v: View?) {
        handleButtonClick(3)
        val button = findViewById<View>(R.id.btnOptionC) as TextView
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        button.startAnimation(animation)
    }

    fun clickOption4(v: View?) {
        handleButtonClick(4)
        val button = findViewById<View>(R.id.btnOptionD) as TextView
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        button.startAnimation(animation)
    }


    fun handleButtonClick(b: Int) {
        opt1.isEnabled=false
        opt2.isEnabled=false
        opt3.isEnabled=false
        opt4.isEnabled=false

        when (b) {
            1 -> if (opt1.text.toString() == cquestion.get(qid - 1).answer.toString()) {
                showDialog(true)
                score++
            } else if (opt1.text.toString() !== cquestion.get(qid - 1).answer.toString()) {
                showDialog(false)
            }
            2 -> if (opt2.text.toString() == cquestion.get(qid - 1).answer.toString()) {
                score++
                showDialog(true)
            } else if (opt2.text.toString() !== cquestion.get(qid - 1).answer) {
                showDialog(false)
            }
            3 -> if (opt3.text.toString() == cquestion.get(qid - 1).answer.toString()) {
                score++
                showDialog(true)
            } else if (opt3.text.toString() !== cquestion.get(qid - 1).answer.toString()) {
                showDialog(false)
            }
            4 -> if (opt4.text.toString() == cquestion.get(qid - 1).answer.toString()) {
                score++
                showDialog(true)
            } else if (opt4.text.toString() !== cquestion.get(qid - 1).answer.toString()) {
                showDialog(false)
            }

        }

        if (qcount == 10) {
            Handler().postDelayed({
                val i = Intent(this@PlayActivity, ResultActivity::class.java)
                qcount = 0
                i.putExtra("Score", score)
                i.putExtra("Category", cat)
                i.putExtra("token", token)
                startActivityForResult(i, 1)
            }, 1000)
        } else {
            Handler().postDelayed({ setQuestionView() }, 500)
        }
        tvScore.setText("Score: $score")
        tvqnum.setText("Question: " + (qn + 1) + "\\" + "10")
    }

    private fun parseJson(out: String?) {
        try {
            val resJson = JSONObject(out)
            val rescode = resJson.getString("response_code")
            val code = rescode.toInt()
            if (code == 0) {
                val result = resJson.getString("results")
                val resArray = JSONArray(result)
                for (i in 0 until resArray.length()) {
                    val qjson = resArray.getJSONObject(i)
                    val q = qjson.getString("question")
                    val cans = qjson.getString("correct_answer")
                    val ians = qjson.getString("incorrect_answers")
                    val ans = JSONArray(ians)
                    val ians1 = ans.getString(0)
                    val ians2 = ans.getString(1)
                    val ians3 = ans.getString(2)
                    val rand = Random()
                    val n = rand.nextInt(4) + 1
                    var que: QuestionClass? = null
                    when (n) {
                        1 -> que = QuestionClass(q, ians1, ians2, ians3, cans)
                        2 -> que = QuestionClass(q, ians1, ians2, cans, ians3)
                        3 -> que = QuestionClass(q, ians1, cans, ians3, ians2)
                        4 -> que = QuestionClass(q, cans, ians2, ians3, ians1)
                    }

                    val cq = QuestionClass(q, cans)
                    if (que != null) {
                        question.add(que)
                    }
                    cquestion.add(cq)
                }
                setQuestionView()
            } else {
                Log.d("May Mali Dito", "No response")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("May Mali Dito", "JSON Error")
        }
    }

    private fun setQuestionView() {

        currentQ = question.get(qid)
        cansQ = cquestion.get(qid)

        txtQuestion.text= Html.fromHtml(currentQ!!.question).toString()
        opt1.text= Html.fromHtml(currentQ!!.optionA.toString())
        opt2.text= Html.fromHtml(currentQ!!.optionB.toString())
        opt3.text= Html.fromHtml(currentQ!!.optionC.toString())
        opt4.text= Html.fromHtml(currentQ!!.optionD.toString())

        if (opt1.text.toString() == "") {
            opt1.visibility=View.GONE
            qtype = false
        } else {
            opt1.visibility=View.VISIBLE
        }
        if (opt2.text.toString() == "") {
            opt2.visibility=View.GONE
            qtype = false
        } else {
            opt2.visibility=View.VISIBLE
        }
        if (opt3.text.toString() == "") {
            qtype = false
            opt3.visibility=View.GONE
        } else {
            opt3.visibility=View.VISIBLE
        }
        if (opt4.text.toString() == "") {
            qtype = false
            opt4.visibility=View.GONE
        } else {
            opt4.visibility = View.VISIBLE
        }
        opt1.isEnabled=true
        opt2.isEnabled=true
        opt3.isEnabled=true
        opt4.isEnabled=true
        qcount++
        qid++
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Bundle bundle = getIntent().getExtras();
        //cat = bundle.getInt("cat");
        loadQ()
    }

    fun loadQ() {
        score = 0
        url = "https://opentdb.com/api.php?amount=10&category=25&type=multiple"
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    //val json = JSONObject(response)
                    if (response != null) {
                        parseJson(response)
                    } else {
                        networkDialog()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Loading... Please wait...$response",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) { error -> error.printStackTrace() }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun showDialog(correct: Boolean) {
        val dialog = Dialog(this@PlayActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null) {
            val colorDrawable = ColorDrawable(Color.TRANSPARENT)
            dialog.window!!.setBackgroundDrawable(colorDrawable)
        }
        dialog.setContentView(R.layout.dialog_correct)
        dialog.setCancelable(false)
        dialog.show()
        onPause()
        val answer: String? = cquestion.get(qid - 1).answer
        val dialogText = dialog.findViewById<TextView>(R.id.correctText)
        val buttonNext = dialog.findViewById<Button>(R.id.dialogNext)
        if (correct) {
            dialogText.setTextColor(Color.parseColor("#00FF00"))
            dialogText.text = "You're correct!"
        } else {
            dialogText.setTextColor(Color.parseColor("#FF0000"))
            dialogText.text = Html.fromHtml(
                "Incorrect " + System.getProperty("line.separator") + "The correct answer is " + System.getProperty(
                    "line.separator"
                ) + answer
            )
        }
        buttonNext.setOnClickListener {
            dialog.dismiss()
            qn++
            timeValue = 15

        }
    }

    private fun networkDialog() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> loadQ()
                    DialogInterface.BUTTON_NEGATIVE -> {}
                }
            }
        val builder = AlertDialog.Builder(getApplicationContext())
        builder.setMessage("Network error occured: Retry?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    fun back(view: View?) {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}