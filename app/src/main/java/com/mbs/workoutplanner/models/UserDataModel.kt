package com.mbs.workoutplanner.models

data class UserDataModel(val name: String, val modality: String, val weight: Double, val height: Long, val bf: Double?) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "modality" to modality,
            "weight" to weight,
            "height" to height,
            "bf" to bf
        )
    }
}

