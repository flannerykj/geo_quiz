package com.example.nerdranch_geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val QUESTION_INDEX_KEY = "question_index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    // private lateinit var viewModel: QuizViewModel
    private val viewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    private lateinit var cheatButton: Button

    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(QUESTION_INDEX_KEY, 0) ?: 0
        viewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)

        textView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            trueButton.isEnabled = false
            falseButton.isEnabled = true
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            falseButton.isEnabled = false
            trueButton.isEnabled = true
           checkAnswer(false)
        }

        cheatButton.setOnClickListener {
            val intent = CheatActivity.newIntent(this, answerIsTrue = viewModel.currentQuestionAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        previousButton.setOnClickListener {
            viewModel.previousQuestion()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            viewModel.nextQuestion()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResourceId = viewModel.currentQuestionText
        textView.setText(questionTextResourceId)
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = viewModel.currentQuestionAnswer
        val messageId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        val toast = Toast.makeText(this, messageId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(QUESTION_INDEX_KEY, viewModel.currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            // no result was sent back
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            // UNFINISHED - show that user is cheater
        }
    }
}
