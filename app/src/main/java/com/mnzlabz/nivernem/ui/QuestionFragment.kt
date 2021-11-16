package com.mnzlabz.nivernem.ui

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mnzlabz.nivernem.R
import com.mnzlabz.nivernem.data.model.Question
import com.mnzlabz.nivernem.databinding.FragmentQuestionBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel
import kotlinx.coroutines.NonCancellable.cancel

class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var errorCount: Int = 0
    private lateinit  var currentQuestion: Question
    private lateinit var fade: Animation
    private lateinit var bounce: Animation
    private lateinit var _context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.let {
            _context = it.context
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        sharedPreferences = _context.getSharedPreferences("QUIZ_PREFERENCES", Context.MODE_PRIVATE)
        binding.buttons.btnNext.visibility = View.GONE

        initializeListeners()
        initializeObservers()
        initializeQuestion()
        initializeAnimations()

        return binding.root
    }

    private fun initializeObservers() {
        gameViewModel.hasFinished.observe(viewLifecycleOwner, Observer {
            with(binding) {
                buttons.btnNext.visibility = if (gameViewModel.finished()) View.VISIBLE else View.GONE
            }
        })

        gameViewModel.currentLevel.observe(viewLifecycleOwner, Observer {
            sharedPreferences.edit().putInt(getString(R.string.saved_high_score_key), gameViewModel.currentLevel.value!!).commit()
            if(!gameViewModel.finished()) initializeQuestion()
        })
    }

    private fun initializeAnimations() {
        fade = AnimationUtils.loadAnimation(layoutInflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(layoutInflater.context, R.anim.bounce)

        binding.apply {
            question.cardQuestion.startAnimation(fade)
            answerContainer.root.startAnimation(fade)
            buttons.root.startAnimation(bounce)
        }
    }

    private fun initializeListeners() {
        with(binding) {
            buttons.btnNext.setOnClickListener {
                it.findNavController().navigate(R.id.action_questionFragment_to_finalGameFragment)
            }

            buttons.btnPrevious.setOnClickListener {
                it.findNavController().navigate(R.id.action_questionFragment_to_menuFragment)
            }

            answerContainer.btnAnswer.setOnClickListener {
                if(binding.answerContainer.edtAnswer.text.toString().lowercase() == currentQuestion.answer.lowercase()) {
                    saveAnswer()
                    showAnswer("Yooow")
                } else if (errorCount == 3) {
                    saveAnswer()
                    showAnswer("Errooou")
                } else {
                    errorCount++
                    binding.answerContainer.txtErrorCount.text = "${errorCount} erro(s)"
                }
            }
        }


    }

    private fun saveAnswer() {
        errorCount = 0
        sharedPreferences.edit().putInt(getString(R.string.saved_high_score_key), currentQuestion.id).commit()
    }

    private fun showAnswer(title: String) {
        MaterialAlertDialogBuilder(layoutInflater.context)
            .setTitle(title)
            .setMessage(currentQuestion.answer)
            .setPositiveButton(resources.getString(R.string.puuts)) { dialog, which ->
                if(!gameViewModel.finished()) gameViewModel.levelUp()
            }
            .show()
    }

    private fun initializeQuestion() {
        if(!gameViewModel.finished()) {
            fade = AnimationUtils.loadAnimation(layoutInflater.context, R.anim.fade_in)
            errorCount = 0

            with(binding) {
                answerContainer.txtErrorCount.text = "${errorCount} erro(s)"
                answerContainer.edtAnswer.text?.clear()
                currentQuestion = gameViewModel.quiz.value?.get(gameViewModel.currentLevel.value!!) as Question
                question.questionBody.startAnimation(fade)
                question.questionBody.text = currentQuestion.text
            }
        } else {
            this.findNavController().navigate(R.id.action_questionFragment_to_finalGameFragment)
        }
    }
}