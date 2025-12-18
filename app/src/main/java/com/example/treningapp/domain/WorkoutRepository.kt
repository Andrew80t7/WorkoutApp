package com.example.treningapp.domain

import com.example.treningapp.data.local.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {


    suspend fun addWorkout(workout: WorkoutEntity)

    suspend fun updateWorkout(workout: WorkoutEntity)

    suspend fun getWorkoutById(workoutId: String): WorkoutEntity?

    suspend fun deleteWorkout(workoutId: String)

    fun getAllWorkouts(): Flow<List<WorkoutEntity>>


    fun getWorkoutsByDate(startDate: Long, endDate: Long): Flow<List<WorkoutEntity>>


    fun getAllWorkoutDates(): Flow<List<Long>>

}