package com.mbs.workoutplanner.view.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.databinding.ActivityLoginBinding
import com.mbs.workoutplanner.view.fragments.SignInFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth
        if (auth.currentUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            supportFragmentManager.beginTransaction().replace(binding.root.id, SignInFragment())
                .commit()
        }
    }
}