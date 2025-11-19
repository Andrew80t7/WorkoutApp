package com.example.treningapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.treningapp.data.local.entities.WorkoutEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkoutById(workout: String)

    @Query("SELECT * FROM workouts WHERE workoutId = :id")
    suspend fun getWorkoutById(id: String): WorkoutEntity?


    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>



}