package com.mnzlabz.nivernem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mnzlabz.nivernem.R
import com.mnzlabz.nivernem.adapter.SecretMessageAdapter
import com.mnzlabz.nivernem.databinding.FragmentMenuBinding
import com.mnzlabz.nivernem.databinding.FragmentSecretMessageBinding
import com.mnzlabz.nivernem.viewmodel.GameViewModel

class SecretMessageFragment : Fragment() {
    private lateinit var binding: FragmentSecretMessageBinding
    private val adapter by lazy { SecretMessageAdapter() }
    private lateinit var fade: Animation
    private lateinit var bounce: Animation

    companion object {
        @JvmStatic val fragment = SecretMessageFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_secret_message, container, false)
        binding.rvSecretMessages.adapter = adapter

        initializeAnimations(inflater)
        initializeData()
        initializeListeners()

        return binding.root
    }

    private fun initializeListeners() {
//        with(binding) {
//            btnStart.setOnClickListener {
//                it.findNavController().navigate(R.id.action_menuFragment_to_welcomeFragment)
//            }
//            btnReadSecret.setOnClickListener {
//                if(hasFinishedGame) {
//                    //it.findNavController().navigate(R.id.action_menuFragment_to_welcomeFragment)
//                    Toast.makeText(context, "DEV", Toast.LENGTH_LONG).show()
//                } else {
//                    Toast.makeText(context, "Voce precisa ter 101 pontos para ver a mensagem secreta =(", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.rvSecretMessages.startAnimation(fade)
    }

    private fun initializeData() {

    }
}