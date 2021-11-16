package com.mnzlabz.nivernem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mnzlabz.nivernem.data.model.Question

class GameViewModel: ViewModel() {
    private val _lastLevel: Int = 101

    private var _quiz = MutableLiveData<List<Question>>()
    val quiz : LiveData<List<Question>> = _quiz

    private var _currentLevel = MutableLiveData<Int>()
    val currentLevel : LiveData<Int> = _currentLevel

    private var _hasFinished = MutableLiveData<Boolean>()
    val hasFinished : LiveData<Boolean> = _hasFinished

    fun initializeQuestions(questions: List<Question>) {
        _quiz.value = questions
    }

    fun initializeLevelState(level: Int) {
        _currentLevel.value = level
    }

    fun levelUp() {
        _currentLevel?.let {
            it?.value = it.value?.plus(1)
            _hasFinished.value = it.value == _lastLevel
        }
    }

    fun resetGame() {
        _currentLevel.value = 0
        _hasFinished.value = false
    }

    fun finished(): Boolean {
        return (_hasFinished?.value == true) || (currentLevel.value == _lastLevel)
    }

}