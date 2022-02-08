package com.example.aclite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.aclite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE

        binding.button.setOnClickListener {
            viewModel.onTryLogin(
                binding.user.text.toString(),
                binding.password.text.toString()
            )
        }

        viewModel.state.observe(this@MainActivity){ state ->
            binding.user.error = state.userError?.let(::getString)
            binding.password.error = state.passError?.let(::getString)
            binding.button.visibility = if (state.loggingIn) View.INVISIBLE else View.VISIBLE
            binding.progressBar.visibility = if(state.loggingIn) View.VISIBLE else View.GONE

            if(state.loggedIn){
                startActivity(Intent(this, NextActivity::class.java))
                viewModel.onNavigateToNextScreen()
            }
        }
    }
}