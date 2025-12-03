package com.example.treningapp.data.repository

import com.example.treningapp.data.local.dao.ExerciseDao
import com.example.treningapp.data.local.entities.ExerciseEntity
import com.example.treningapp.domain.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class ExerciseRepositoryImpl(
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {
    override suspend fun addExercise(exercise: ExerciseEntity) {
        exerciseDao.insertExercise(exercise)
    }

    override suspend fun getExerciseById(exerciseId: String): ExerciseEntity? {
        return exerciseDao.getExerciseById(exerciseId)
    }

    override suspend fun updateExercise(exercise: ExerciseEntity) {
        exerciseDao.updateExercise(exercise)
    }

    override suspend fun deleteExercise(exercise: ExerciseEntity) {
        exerciseDao.deleteExercise(exercise)
    }

    override suspend fun getAllExercises(): Flow<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()

    }


}