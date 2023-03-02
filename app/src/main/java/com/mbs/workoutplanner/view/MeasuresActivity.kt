package com.mbs.workoutplanner.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mbs.workoutplanner.MeasuresViewModel
import com.mbs.workoutplanner.databinding.ActivityMeasuresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasuresActivity : AppCompatActivity() {
    private var genero: Int? = null
    private var fragment: Int? = null
    private lateinit var binding: ActivityMeasuresBinding
    private val viewModel: MeasuresViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasuresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        genero = intent.extras?.getInt(GENDER)
        fragment = intent.extras?.getInt(FRAGMENT)
        if (fragment == 0) {
            viewModel.setGender(genero)
        }
        placeFragment()
    }

    private fun placeFragment() {
        val frag = if (fragment == 1) {
            ImcCalculateFragment()
        } else {
            BfCalculateFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, frag).commit()
    }


    companion object {

        private const val GENDER = "sex"
        private const val FRAGMENT = "fragment"

        /** @return a new intent of this activity.
         *  @param gender 0 for women, 1 for men.
         *  @param fragmentToPlace 0 for bf, 1 for imc.*/
        fun newInstance(gender: Int?, fragmentToPlace: Int, context: Context): Intent {
            val activity = Intent(context, MeasuresActivity::class.java)
            if (gender != null) {
                activity.putExtra(GENDER, gender)
            }
            activity.putExtra(FRAGMENT, fragmentToPlace)
            return activity
        }
    }
}