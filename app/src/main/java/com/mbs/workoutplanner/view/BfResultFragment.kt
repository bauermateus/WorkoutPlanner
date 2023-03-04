package com.mbs.workoutplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mbs.workoutplanner.MeasuresViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentBfResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BfResultFragment : Fragment() {
    private var _binding: FragmentBfResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MeasuresViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBfResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeBfResults()
        handleGenderBf()
    }

    private fun placeBfResults() {
        viewModel.bfResult.observe(viewLifecycleOwner) {
            binding.bfValue.text = it
        }
    }

    /**Styles the view according the results.*/
    private fun handleGenderBf() {
        viewModel.gender.observe(viewLifecycleOwner) { gender ->
            if (gender == 0) {
                when (binding.bfValue.text.toString().toFloat()) {
                    in 0f..12f -> binding.bfQualidade.text = "Fisiculturista"
                    in 12f..17f -> binding.bfQualidade.text = "Alto nível de definição"
                    in 17f..22f -> binding.bfQualidade.text = "Muito bom"
                    in 22f..26f -> binding.bfQualidade.text = "Bom"
                    in 26f..33f -> {
                        binding.bfQualidade.text = "Razoável"
                        binding.bfQualidade.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blur_yellow
                            )
                        )
                    }
                    in 33f..Float.MAX_VALUE -> {
                        binding.bfQualidade.text = "Elevado"
                        binding.bfQualidade.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blur_red
                            )
                        )
                    }
                }
            } else {
                when (binding.bfValue.text.toString().toFloat()) {
                    in 0f..8f -> binding.bfQualidade.text = "Fisiculturista"
                    in 8f..10f -> binding.bfQualidade.text = "Alto nível de definição"
                    in 10f..13f -> binding.bfQualidade.text = "Muito bom"
                    in 13f..17f -> binding.bfQualidade.text = "Bom"
                    in 17f..25f -> {
                        binding.bfQualidade.text = "Razoável"
                        binding.bfQualidade.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blur_yellow
                            )
                        )
                    }
                    in 25f..Float.MAX_VALUE -> {
                        binding.bfQualidade.text = "Elevado"
                        binding.bfQualidade.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blur_red
                            )
                        )
                    }
                }
            }
        }
    }
}