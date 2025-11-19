package com.example.treningapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val exerciseId: String,
    val name: String,
    val targetMuscle: String,
    val isCustom: Boolean = false
)
