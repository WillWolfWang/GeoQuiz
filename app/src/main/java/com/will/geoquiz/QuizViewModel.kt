package com.will.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)
    )
    var currentIndex = 0
    var answerCount = 0
    var correctCount = 0
    val totalCount = questionBank.size
    var isCheater = false

    init {
        Log.d(TAG, "ViewModel init")
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var isAnswer: Boolean = false
        get() = questionBank[currentIndex].isAnswer
        set(value) {
            questionBank[currentIndex].isAnswer = value
            field = value
        }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPre() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex < 0) {
            currentIndex += questionBank.size
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel destroyed")
    }
}