
package com.example.treningapp.di

import android.app.Application
import androidx.room.Room
import com.example.treningapp.data.local.AppDatabase
import com.example.treningapp.data.repository.ExerciseRepositoryImpl
import com.example.treningapp.data.repository.UserRepositoryImpl
import com.example.treningapp.data.repository.WorkoutRepositoryImpl
import com.example.treningapp.domain.ExerciseRepository
import com.example.treningapp.domain.UserRepository
import com.example.treningapp.domain.WorkoutRepository
import com.example.treningapp.ui.main.HomeViewModel
import com.example.treningapp.ui.main.MainViewModel
import com.example.treningapp.ui.main.RegisterViewModel
import com.example.treningapp.auth.AuthViewModel
import com.example.treningapp.data.local.UserPreferences
import com.example.treningapp.ui.main.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Room DB
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "treningapp-db"
        ).fallbackToDestructiveMigration().build()
    }

    // DAOs
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().workoutDao() }
    single { get<AppDatabase>().exerciseDao() }


    // 1) UserPreferences (DataStore wrapper)
    single { UserPreferences(androidContext()) }   // <- вот эта строка


    // Repositories (интерфейс -> реализация)
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
    single<WorkoutRepository> { WorkoutRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), /* get(UserPreferences) */ get()) } // передай prefs, если есть

    // ViewModels
    viewModel { AuthViewModel() }
    viewModel { ProfileViewModel(get()) }    // profile repo = UserRepository_impl
    viewModel { HomeViewModel(get()) }       // HomeViewModel(userRepository)
    viewModel { MainViewModel(get()) }       // MainViewModel(userRepository)
    viewModel { RegisterViewModel(get()) }   // RegisterViewModel(userRepository)
}
