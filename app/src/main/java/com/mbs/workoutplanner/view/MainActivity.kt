package com.mbs.workoutplanner.view

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        setupBottomNavigationWithNavController()
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
        if (auth.currentUser == null) {
            val signInLauncher = registerForActivityResult(
                FirebaseAuthUIActivityResultContract()
            ) { res ->
                this.onSignInResult(res)
            }

            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            signInLauncher.launch(signInIntent)
        }
    }

    override fun onStart() {
        super.onStart()


    }
    private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult?) {
        val response = res?.idpResponse

        if (res?.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser

            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Não foi possível fazer login. Tente Novamente...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigationWithNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavegationView
        bottomNavigationView.setupWithNavController(navController)
    }
}