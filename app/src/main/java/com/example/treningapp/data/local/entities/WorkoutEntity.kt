package com.example.treningapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey val workoutId: String,
    val userId: String,
    val name: String,
    val startTime: Long,
    val endTime: Long?,
    val notes: String? = null,
    val workoutType: String,
    val workoutLevel: String,
)
