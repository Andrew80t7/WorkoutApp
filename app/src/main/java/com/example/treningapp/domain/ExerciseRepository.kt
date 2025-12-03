package com.example.treningapp.domain

import com.example.treningapp.data.local.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    suspend fun addExercise(exercise: ExerciseEntity)

    suspend fun getExerciseById(exerciseId: String): ExerciseEntity?

    suspend fun updateExercise(exercise: ExerciseEntity)

    suspend fun deleteExercise(exercise: ExerciseEntity)

    suspend fun getAllExercises(): Flow<List<ExerciseEntity>>







}