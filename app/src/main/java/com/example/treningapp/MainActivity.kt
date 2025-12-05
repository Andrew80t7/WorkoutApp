package com.example.treningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.treningapp.navigation.AppNavigation
import com.example.treningapp.ui.theme.TreningAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TreningAppTheme {
                AppNavigation()
            }
        }
    }
}