package com.example.treningapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.treningapp.ui.main.DayViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayScreen(
    date: LocalDate,
    onBack: () -> Unit,
    modifier: Modifier,
    onAddWorkout: () -> Unit,
    viewModel: DayViewModel = koinViewModel()
) {

    LaunchedEffect(date) {
        viewModel.setDate(date)
    }

    val workouts by viewModel.workoutsForDay.collectAsState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddWorkout) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Тренировки за ${viewModel.selectedDate}", style = MaterialTheme.typography.headlineSmall)

            if (workouts.isEmpty()) {
                Text("В этот день вы отдыхали 😴", color = Color.Gray)
            } else {
                LazyColumn {
                    items(workouts) { workout ->
                        WorkoutCard(workout)
                    }
                }
            }
        }
    }
}