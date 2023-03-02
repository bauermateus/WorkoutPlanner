package com.mbs.workoutplanner.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mbs.workoutplanner.HomeAdapter
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.repository.WorkoutRepository
import com.mbs.workoutplanner.databinding.ActivityMainBinding
import com.mbs.workoutplanner.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationWithNavController()
    }

    private fun setupBottomNavigationWithNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavegationView
        bottomNavigationView.setupWithNavController(navController)
    }
}