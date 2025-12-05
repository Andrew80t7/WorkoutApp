package com.example.treningapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class SetEntity(
    @PrimaryKey val setId: String,
    val parentWorkoutExerciseId: String,
    val reps: Int,
    val weight: Double,
    val setOrder: Int
)