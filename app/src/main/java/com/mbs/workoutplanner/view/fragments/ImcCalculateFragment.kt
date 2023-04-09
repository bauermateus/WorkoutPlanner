package com.mbs.workoutplanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mbs.workoutplanner.view.viewmodels.MeasuresViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentImcCalculateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImcCalculateFragment : Fragment() {
    private var _binding: FragmentImcCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MeasuresViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentImcCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //handleInputsAndOpenResultFragment()
        binding.calculateButton.setOnClickListener {
            handleInputsAndOpenResultFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handleInputsAndOpenResultFragment() {
        binding.calculateButton.setOnClickListener {
            if (binding.pesoEdit.text.isNullOrBlank()) {
                binding.pesoInputLayout.error = getString(R.string.peso_obrigatorio_erro)
                binding.alturaInputLayout.error = null
            } else if (binding.alturaEdit.text.isNullOrBlank()) {
                binding.alturaInputLayout.error = getString(R.string.altura_obrigatório_erro)
                binding.pesoInputLayout.error = null
            } else {
                binding.pesoInputLayout.error = null
                binding.alturaInputLayout.error = null
                viewModel.calculateImc(
                    binding.alturaEdit.text.toString().toFloat(),
                    binding.pesoEdit.text.toString().toFloat()
                )
                binding.pesoEdit.text?.clear()
                binding.alturaEdit.text?.clear()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ImcResultFragment())
                    .addToBackStack(null).commit()
            }
        }
    }
}