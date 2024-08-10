package com.will.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    // lateinit 告诉编译器稍后初始化
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: ImageButton
    private lateinit var tvQuestion: TextView

    // by lazy 只调用一次，并且在 onCreate 之后才被调用
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate-->");
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX)?:0
        Log.d(TAG, "savedInstanceState-->$currentIndex")
        quizViewModel.currentIndex = currentIndex


        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        tvQuestion = findViewById(R.id.tv_question)
        tvQuestion.setOnClickListener{
//            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        btnTrue.setOnClickListener {view: View ->
            // this 后面跟 @ 符号
//            val toast = Toast.makeText(this@MainActivity, R.string.correct_toast, Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.TOP, 0, 0)
//            toast.show()
            checkAnswer(true)
            btnTrue.isEnabled = false
            btnFalse.isEnabled = false
        }

        btnFalse.setOnClickListener { view:View ->
            checkAnswer(false)
            btnTrue.isEnabled = false
            btnFalse.isEnabled = false
        }

        btnNext.setOnClickListener {view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        findViewById<View>(R.id.btn_pre).setOnClickListener{
            quizViewModel.moveToPre()
            updateQuestion()
        }

        updateQuestion()

//        val provider: ViewModelProvider = ViewModelProvider(this)
//        val quizViewModel = provider.get(QuizViewModel::class.java)
//        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart-->");
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume-->");
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause-->");
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState-->");
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop-->");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy-->");
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        tvQuestion.setText(questionTextResId)
        btnTrue.isEnabled = !quizViewModel.isAnswer
        btnFalse.isEnabled = !quizViewModel.isAnswer
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer;

        val messageResId = if (correctAnswer == userAnswer) {
            quizViewModel.correctCount++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        quizViewModel.answerCount++
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        quizViewModel.isAnswer = true
        if (quizViewModel.answerCount == quizViewModel.totalCount) {
            Toast.makeText(this, "Score:" + quizViewModel.correctCount * 1.0f / quizViewModel.answerCount * 100 + "%", Toast.LENGTH_SHORT).show()
        }
    }
}