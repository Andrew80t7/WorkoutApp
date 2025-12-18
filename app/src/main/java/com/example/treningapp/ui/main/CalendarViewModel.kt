package com.example.treningapp.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treningapp.domain.WorkoutRepository
import com.example.treningapp.utils.toLocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth
import kotlin.collections.emptySet

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {


    var currentMonth by mutableStateOf(YearMonth.now())
        private set

    val workoutDates = repository.getAllWorkoutDates()
        .map { list -> list.map { it.toLocalDate() }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())



}