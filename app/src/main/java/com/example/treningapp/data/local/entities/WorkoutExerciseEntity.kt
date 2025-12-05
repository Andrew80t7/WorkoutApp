package com.example.treningapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "workout_exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["workoutId"],
            childColumns = ["parentWorkoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutExerciseEntity(
    @PrimaryKey val id: String,
    val parentWorkoutId: String,
    val originalExerciseId: String,
    val note: String? = null
)
