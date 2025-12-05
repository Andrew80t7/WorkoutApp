package com.example.treningapp.data.repository

import com.example.treningapp.data.local.dao.WorkoutDao
import com.example.treningapp.data.local.entities.WorkoutEntity
import com.example.treningapp.domain.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImpl(
    private val workoutDao: WorkoutDao
) : WorkoutRepository {
    override suspend fun addWorkout(workout: WorkoutEntity) {
      return  workoutDao.insertWorkout(workout)
        }

    override suspend fun updateWorkout(workout: WorkoutEntity) {
      workoutDao.updateWorkout(workout)
    }

    override suspend fun getWorkoutById(workoutId: String): WorkoutEntity? {
      return  workoutDao.getWorkoutById(workoutId);
    }

    override suspend fun deleteWorkout(workoutId: String) {
        workoutDao.deleteWorkoutById(workoutId)
    }

    override fun getAllWorkouts(): Flow<List<WorkoutEntity>> {
       return workoutDao.getAllWorkouts()
    }


}