package com.mnzlabz.nivernem.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mnzlabz.nivernem.R
import com.mnzlabz.nivernem.data.model.SecretMessage
import com.mnzlabz.nivernem.databinding.FragmentMenuBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var hasFinishedGame: Boolean = false
    private lateinit var fade: Animation
    private lateinit var bounce: Animation
    private lateinit var _context: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container?.let {
            _context = it.context
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        sharedPreferences = _context.getSharedPreferences("QUIZ_PREFERENCES", Context.MODE_PRIVATE)

        initializeAnimations(inflater)
        initializeListeners()
        initializeObservers()

        binding.gameMenu.menuScore.text = "Level ${gameViewModel.currentLevel.value.toString()}"

        return binding.root
    }

    private fun initializeObservers() {
        gameViewModel.currentLevel.observe(viewLifecycleOwner, Observer {
            binding.gameMenu.menuScore.text = "Level ${gameViewModel.currentLevel.value.toString()}"
        })
    }

    private fun initializeListeners() {
        with(binding.gameMenu) {
            btnStart.setOnClickListener {
                it.findNavController().navigate(R.id.action_menuFragment_to_welcomeFragment)
            }
            btnBonus.setOnClickListener {
                if(gameViewModel.finished()) {
                    it.findNavController().navigate(R.id.action_menuFragment_to_secretMessageActivity)
                } else {
                    showAlert(getString(R.string.title_unauthorized_message), getString(R.string.message_unauthorized))
                }
            }
            btnReset.setOnClickListener {
                sharedPreferences.edit().putInt(getString(R.string.saved_high_score_key), 0).commit()
                gameViewModel.resetGame()
            }

        }
    }

    private fun showAlert(title: String, message: String) {
        MaterialAlertDialogBuilder(layoutInflater.context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
            }
            .show()
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.gameMenu.cardMenu.startAnimation(fade)
        binding.gameMenu.btnStart.startAnimation(bounce)
        binding.gameMenu.btnBonus.startAnimation(bounce)
        binding.gameMenu.btnReset.startAnimation(bounce)
    }
}