package com.example.treningapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.treningapp.data.local.dao.ExerciseDao
import com.example.treningapp.data.local.dao.UserDao
import com.example.treningapp.data.local.dao.WorkoutDao
import com.example.treningapp.data.local.entities.ExerciseEntity
import com.example.treningapp.data.local.entities.SetEntity
import com.example.treningapp.data.local.entities.UserEntity
import com.example.treningapp.data.local.entities.WorkoutEntity
import com.example.treningapp.data.local.entities.WorkoutExerciseEntity


@Database(
    entities = [
        UserEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        SetEntity::class,
        ExerciseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao


}