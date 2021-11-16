package com.mnzlabz.nivernem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mnzlabz.nivernem.R
import com.mnzlabz.nivernem.adapter.SecretMessageAdapter
import com.mnzlabz.nivernem.data.model.SecretMessage
import com.mnzlabz.nivernem.databinding.ActivitySecretMessageBinding

class SecretMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecretMessageBinding
    private val adapter by lazy { SecretMessageAdapter() }
    private var messages: MutableList<SecretMessage> = mutableListOf()
    private lateinit var fade: Animation
    private lateinit var bounce: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_secret_message)
        initializeData()

        binding.rvSecretMessages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSecretMessages.adapter = adapter

        initializeAnimations(layoutInflater)
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.rvSecretMessages.startAnimation(fade)
    }

    private fun initializeData() {

        messages = mutableListOf(
            SecretMessage(getString(R.string.name_evelyn), getString(R.string.message_evelyn)),
            SecretMessage(getString(R.string.name_dry), getString(R.string.message_dry)),
            SecretMessage(getString(R.string.name_philippe), getString(R.string.message_philippe))
        )

        adapter.submitList(messages)
    }
}