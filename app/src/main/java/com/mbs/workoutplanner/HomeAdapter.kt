package com.mbs.workoutplanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mbs.workoutplanner.databinding.WorkoutRecyclerContentBinding
import com.mbs.workoutplanner.models.WorkoutModel

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var workoutList: AsyncListDiffer<WorkoutModel> = AsyncListDiffer(this, DiffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = WorkoutRecyclerContentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(workoutList.currentList[position])
    }

    override fun getItemCount(): Int {
        return workoutList.currentList.size
    }

    fun updateList(list: List<WorkoutModel>) {
        workoutList.submitList(list)
    }


    object DiffCallBack : DiffUtil.ItemCallback<WorkoutModel>() {
        override fun areItemsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: WorkoutRecyclerContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: WorkoutModel) {
            binding.workoutName.text = workout.title
            binding.numOfExercices.text = workout.numberOfExercise.toString()
            binding.day.text = workout.weekDay

        }
    }
}
