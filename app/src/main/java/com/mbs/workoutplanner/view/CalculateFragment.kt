package com.mbs.workoutplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.repository.WorkoutRepository
import com.mbs.workoutplanner.databinding.FragmentCalculateBinding


class CalculateFragment : Fragment() {
    private var _binding: FragmentCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModel.Factory(WorkoutRepository(AppDataBase.getInstance(requireContext())))
        TODO("repositorio provisorio?")
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

