package com.mnzlabz.nivernem

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mnzlabz.nivernem.data.model.Question
import com.mnzlabz.nivernem.databinding.ActivityMainBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()
    private var questionsDb: List<Question> = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPreferences = getSharedPreferences("QUIZ_PREFERENCES", Context.MODE_PRIVATE)

        initializeGameState()
        initalizeQuestions()

        gameViewModel.initializeQuestions(questionsDb)
        gameViewModel.initializeLevelState(getGameState())
    }

    private fun initializeGameState() {
        val state = sharedPreferences.getInt(getString(R.string.saved_high_score_key), -1)
        if(state == -1) sharedPreferences.edit().putInt(getString(R.string.saved_high_score_key), 0).commit()
    }

    private fun getGameState(): Int {
        return sharedPreferences.getInt(getString(R.string.saved_high_score_key), -1)
    }

    private fun initalizeQuestions() {
        val questions = getQuestionsFromJSON()
        questionsDb = questions
    }

    private fun getQuestionsFromJSON(): List<Question> {
        val gson = Gson()
        val jsonFileString = getJsonDataFromAsset(applicationContext, "quizdb.json")
        val listPersonType = object : TypeToken<List<Question>>() {}.type

        return gson.fromJson(jsonFileString, listPersonType)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}