package com.mnzlabz.nivernem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.mnzlabz.nivernem.R
import com.mnzlabz.nivernem.databinding.FragmentWelcomeBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var fade: Animation
    private lateinit var bounce: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        setListeners()
        initializeAnimations(inflater)
        return binding.root
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.cardWelcome.startAnimation(fade)
        binding.btnStartGame.startAnimation(bounce)
    }

    private fun setListeners() {
        with(binding) {
            btnStartGame.setOnClickListener {
                it.findNavController().navigate(R.id.action_welcomeFragment_to_questionFragment)
            }
        }
    }
}