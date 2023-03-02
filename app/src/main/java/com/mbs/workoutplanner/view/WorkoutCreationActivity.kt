package com.mbs.workoutplanner.view

import android.R.layout.simple_spinner_dropdown_item
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mbs.workoutplanner.MainViewModel
import com.mbs.workoutplanner.dataBase.AppDataBase
import com.mbs.workoutplanner.databinding.ActivityWorkoutCreationBinding
import com.mbs.workoutplanner.repository.WorkoutRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutCreationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutCreationBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleWeekdaySpinner()
        handleNonEditText(binding.workoutCreationRoot, binding.weekdaySpinner)
    }

    private fun handleWeekdaySpinner() {
        val weekdaySpinner = binding.weekdaySpinner
        val items = arrayOf(
            "Selecione...", "Segunda", "Terça",
            "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"
        )
        val adapter = ArrayAdapter(this, simple_spinner_dropdown_item, items)
        weekdaySpinner.adapter = adapter
        weekdaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position == 0) weekdaySpinner.setSelection(0, false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                weekdaySpinner.setSelection(0, false)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //Fecha o teclado ao clicar fora de um EditText
    private fun handleNonEditText(vararg views: View) {
        views.forEach {
            it.setOnTouchListener { v, _ ->
                hideKeyboard(v)
                v.rootView.clearFocus()
                v.performClick()
                false
            }
        }
    }


    /* private fun handleSubmitButton() {
          binding.submitButton.setOnClickListener {
             val workoutInput = WorkoutEntity(
                 id = binding.id.editableText.toString().toInt(),
                 title = binding.title.editableText.toString(),
                 weekDay = binding.weekday.editableText.toString(),
                 numberOfExercise = binding.numberOfExercises.editableText.toString().toInt())
             viewModel.insertWorkout(workoutInput)
    } */
}