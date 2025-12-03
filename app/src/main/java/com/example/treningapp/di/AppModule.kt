package com.example.treningapp.di

import android.content.Context
import androidx.room.Room
import com.example.treningapp.data.local.AppDatabase
import com.example.treningapp.data.local.UserPreferences
import com.example.treningapp.data.repository.UserRepositoryImpl
import com.example.treningapp.data.repository.WorkoutRepositoryImpl
import com.example.treningapp.domain.UserRepository
import com.example.treningapp.domain.WorkoutRepository
import com.example.treningapp.ui.main.HomeViewModel
import com.example.treningapp.ui.main.MainViewModel
import com.example.treningapp.ui.main.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "trenapp_db"
        ).build()
    }


    singleOf(::UserPreferences)

    viewModelOf(::MainViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)

    single { get<AppDatabase>().workoutDao() }
    single { get<AppDatabase>().userDao() }


    singleOf(::WorkoutRepositoryImpl) bind WorkoutRepository::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class

    //case
    TODO("сделать кейсы")


    // viewModelOf
    TODO("сделать viewModel")



}