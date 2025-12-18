package com.example.treningapp.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treningapp.domain.WorkoutRepository
import com.example.treningapp.utils.toEndOfDayTimestamp
import com.example.treningapp.utils.toStartOfDayTimestamp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DayViewModel(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    val dateString = savedStateHandle.get<String>("date") ?: LocalDate.now().toString()

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val workoutsForDay = _selectedDate.flatMapLatest { date ->
        repository.getWorkoutsByDate(
            startDate = date.toStartOfDayTimestamp(),
            endDate = date.toEndOfDayTimestamp()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}