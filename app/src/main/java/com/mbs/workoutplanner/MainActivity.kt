package com.mbs.workoutplanner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.mbs.workoutplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WorkoutViewModel by viewModels {
        WorkoutViewModel.Factory(WorkoutRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.workoutButton.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_workoutFragment)
        }
        binding.calculateButton.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_calculateFragment)
        }
        binding.profileButton.setOnClickListener {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_profileFragment)
        }
    }
}