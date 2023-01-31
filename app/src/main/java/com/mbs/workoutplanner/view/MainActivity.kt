package com.mbs.workoutplanner.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mbs.workoutplanner.HomeAdapter
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.repository.WorkoutRepository
import com.mbs.workoutplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(WorkoutRepository(AppDataBase.getInstance(applicationContext)))
    }
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