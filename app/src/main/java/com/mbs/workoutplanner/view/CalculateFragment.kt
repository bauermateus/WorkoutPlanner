package com.mbs.workoutplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentCalculateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateFragment : Fragment() {
    private var _binding: FragmentCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBfCalculateButton()
        handleImcCalculateButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleBfCalculateButton() {
        binding.buttonCalculateBf.setOnClickListener {

            when (binding.toggleGroup.checkedButtonId) {
                R.id.men_selected_button -> {
                    startActivity(MeasuresActivity.newInstance(1, 0, requireContext()))
                }
                R.id.women_selected_button -> {
                    startActivity(MeasuresActivity.newInstance(0, 0, requireContext()))
                }
                else -> Snackbar.make(
                    binding.root,
                    "É preciso selecionar um gênero.",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun handleImcCalculateButton() {
        binding.buttonCalculateImc.setOnClickListener {
            startActivity(MeasuresActivity.newInstance(null, 1, requireContext()))
        }
    }
}

