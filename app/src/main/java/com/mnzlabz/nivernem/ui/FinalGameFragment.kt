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
import com.mnzlabz.nivernem.databinding.FragmentFinalGameBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel

class FinalGameFragment : Fragment() {
    private lateinit var binding: FragmentFinalGameBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var fade: Animation
    private lateinit var bounce: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_final_game, container, false)

        initializeListeners()
        initializeAnimations(inflater)
        initializeData()

        return binding.root
    }
    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.finishGameMessage.cardEndGame.startAnimation(fade)
        binding.finishGameMessage.root.startAnimation(fade)
        binding.finishGameMessage.btnBackToMenu.startAnimation(bounce)
    }

    private fun initializeListeners() {
        with(binding.finishGameMessage) {
            btnBackToMenu.setOnClickListener {
                it.findNavController().navigate(R.id.action_finalGameFragment_to_menuFragment)
            }
        }
    }

    private fun initializeData() {

    }
}