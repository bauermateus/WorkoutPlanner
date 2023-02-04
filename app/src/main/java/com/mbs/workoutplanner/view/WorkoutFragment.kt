package com.mbs.workoutplanner.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentController
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mbs.workoutplanner.HomeAdapter
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.databinding.FragmentWorkoutBinding
import com.mbs.workoutplanner.repository.WorkoutRepository

class WorkoutFragment : Fragment() {

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModel.Factory(WorkoutRepository(AppDataBase.getInstance(requireContext())))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HomeAdapter()
        val recyclerView = binding.homeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        viewModel.workoutsList.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
        handleFab()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllWorkouts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleFab() {
        binding.homeFab.setOnClickListener {
            findNavController().navigate(
                R.id.action_workoutFragment_to_workoutCreationActivity
            )
        }
    }
}