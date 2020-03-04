package com.example.nerdranch_geoquiz

import androidx.lifecycle.ViewModel
import org.w3c.dom.Text

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question( R.string.question_australia,true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex: Int = 0
    var isCheater = false
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
    get() = questionBank[currentIndex].textResId

    fun nextQuestion() {
        currentIndex  = (currentIndex + 1) % questionBank.size
    }
    fun previousQuestion() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex < 0) {
            currentIndex = questionBank.size - 1
        }
    }
}