package com.mbs.workoutplanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.dataBase.WorkoutEntity
import com.mbs.workoutplanner.databinding.FragmentWorkoutBinding
import com.mbs.workoutplanner.databinding.FragmentWorkoutCreationBinding
import com.mbs.workoutplanner.repository.WorkoutRepository

class WorkoutCreationFragment : Fragment() {
    private var _binding: FragmentWorkoutCreationBinding? = null
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
        _binding = FragmentWorkoutCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSubmitButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Lidar com o clique do botão de criar o treino.
    private fun handleSubmitButton() {
        binding.submitButton.setOnClickListener {
            val workoutInput = WorkoutEntity(
                id = binding.id.editableText.toString().toInt(),
                title = binding.title.editableText.toString(),
                weekDay = binding.weekday.editableText.toString(),
                numberOfExercise = binding.numberOfExercises.editableText.toString().toInt()
            )
            viewModel.insertWorkout(workoutInput)
        }
    }
}