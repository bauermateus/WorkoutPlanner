package com.mbs.workoutplanner.view.activitys

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.view.viewmodels.UserViewModel
import com.mbs.workoutplanner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    val bottomNavigationView: BottomNavigationView get() =  binding.bottomNavegationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationWithNavController(bottomNavigationView)
        setupDividerColor()
        userViewModel.getUserData()
    }

    private fun setupBottomNavigationWithNavController(bottomNavigationView: BottomNavigationView) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupDividerColor() {
        val isDarkModeOn =
            this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        if (isDarkModeOn) {
            @RequiresApi(Build.VERSION_CODES.P)
            window.navigationBarDividerColor = ContextCompat.getColor(this, R.color.black)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_blue)
        } else {
            @RequiresApi(Build.VERSION_CODES.P)
            window.navigationBarDividerColor = ContextCompat.getColor(this, R.color.dark_blue)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.seed)
        }
    }
}