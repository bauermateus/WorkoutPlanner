package com.mbs.workoutplanner.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mbs.workoutplanner.databinding.DialogFragmentEditProfileBinding
import com.mbs.workoutplanner.models.UserDataModel
import com.mbs.workoutplanner.view.viewmodels.UserViewModel
import kotlinx.coroutines.launch

class EditProfileDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentEditProfileBinding.inflate(inflater, container, false)
        configDialogWidth()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            this.dismiss()
        }
        setupEditProfileButton()
    }

    /** Setup the DialogFragment width to same as current screen size */
    private fun configDialogWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            lifecycleScope.launch {
                val width = requireActivity().windowManager.currentWindowMetrics.bounds.width()
                binding.root.minimumWidth = width
            }
        } else {
            lifecycleScope.launch {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                binding.root.minimumWidth = displayMetrics.widthPixels
            }
        }
    }

    private fun setupEditProfileButton() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                nameInput.error = if (name.text.isNullOrBlank()) "Required field." else null
                modalityInput.error = if (modality.text.isNullOrBlank()) "Required field." else null
                heightInput.error = if (height.text.isNullOrBlank()) "Required field." else null
                weightInput.error = if (weight.text.isNullOrBlank()) "Required field." else null
                if (nameInput.error == null && modalityInput.error == null && heightInput.error == null && weightInput.error == null) {
                    userViewModel.saveUser(
                        UserDataModel(
                            name.text.toString(),
                            modality.text.toString(),
                            weight.text.toString().toDouble(),
                            height.text.toString().toLong(),
                            null
                        )
                    )
                    userViewModel.getUserData()
                    dismiss()
                }
            }
        }
    }
}