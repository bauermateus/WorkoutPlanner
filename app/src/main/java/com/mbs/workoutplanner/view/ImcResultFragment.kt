package com.mbs.workoutplanner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.mbs.workoutplanner.MeasuresViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentImcResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImcResultFragment : Fragment() {
    private var _binding: FragmentImcResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MeasuresViewModel by activityViewModels()
    private var resultado: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentImcResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserver() {
        viewModel.imcResult.observe(viewLifecycleOwner){ data ->
            resultado = data
            handleImcResult()
        }
    }

    private fun handleImcResult() {
        binding.imcValue.text = resultado.toString()
        when {
            resultado in 0.0..18.4 -> {
                binding.imcQualidade.text = getString(R.string.under_weight)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blur_yellow
                    )
                )
            }
            resultado in 18.5..24.9 -> {
                binding.imcQualidade.text = getString(R.string.normal_weight)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            }
            resultado in 25.0..29.9 -> {
                binding.imcQualidade.text = getString(R.string.overweight)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blur_yellow
                    )
                )
            }
            resultado in 30.0..34.9 -> {
                binding.imcQualidade.text = getString(R.string.obesity_I)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blur_yellow
                    )
                )
            }
            resultado in 35.0..39.9 -> {
                binding.imcQualidade.text = getString(R.string.obesity_II)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blur_red
                    )
                )
            }
            resultado > 40.0 -> {
                binding.imcQualidade.text = getString(R.string.obesity_III)
                binding.imcQualidade.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blur_red
                    )
                )
            }
            else -> binding.imcQualidade.text = getString(R.string.ops_error_ocurred)
        }
    }
}