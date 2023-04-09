package com.mbs.workoutplanner.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.mbs.workoutplanner.view.viewmodels.MeasuresViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentBfCalculateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BfCalculateFragment : Fragment() {

    private var _binding: FragmentBfCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MeasuresViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBfCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleGenderAndCalculate()
    }

    private fun handleGenderAndCalculate() {
        viewModel.gender.observe(viewLifecycleOwner) { gender ->

            when (gender) {
                0 -> {
                    binding.calculateButton.setOnClickListener {
                        when {
                            binding.pescocoEdit.text.isNullOrBlank() -> {
                                binding.pescocoInputLayout.error = "Campo obrigatório."
                            }
                            binding.alturaEdit.text.isNullOrBlank() -> {
                                binding.alturaInputLayout.error = "Campo obrigatório."
                            }
                            binding.cinturaEdit.text.isNullOrBlank() -> {
                                binding.cinturaInputLayout.error = "Campo obrigatório."
                            }
                            binding.quadrilEdit.text.isNullOrBlank() -> {
                                binding.quadrilInputLayout.error = "Campo obrigatório."
                            }
                            else -> {
                                viewModel.calculateWomenBf(
                                    binding.cinturaEdit.text.toString().toFloat(),
                                    binding.quadrilEdit.text.toString().toFloat(),
                                    binding.pescocoEdit.text.toString().toFloat(),
                                    binding.alturaEdit.text.toString().toFloat(),
                                )
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, BfResultFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }
                1 -> {
                    binding.quadrilInputLayout.visibility = View.GONE
                    val layout =
                        binding.calculateButton.layoutParams as ConstraintLayout.LayoutParams
                    layout.topToBottom = R.id.cintura_input_layout
                    binding.calculateButton.setOnClickListener {
                        when {
                            binding.pescocoEdit.text.isNullOrBlank() -> {
                                binding.pescocoInputLayout.error = "Campo obrigatório."
                            }
                            binding.alturaEdit.text.isNullOrBlank() -> {
                                binding.alturaInputLayout.error = "Campo obrigatório."
                            }
                            binding.cinturaEdit.text.isNullOrBlank() -> {
                                binding.cinturaInputLayout.error = "Campo obrigatório."
                            }
                            else -> {
                                viewModel.calculateMenBf(
                                    binding.cinturaEdit.text.toString().toFloat(),
                                    binding.pescocoEdit.text.toString().toFloat(),
                                    binding.alturaEdit.text.toString().toFloat(),
                                )
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, BfResultFragment())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }
                else -> {
                    Snackbar.make(binding.root, "Um erro ocorreu.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}