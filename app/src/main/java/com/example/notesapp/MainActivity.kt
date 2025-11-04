package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.screens.MainScreen
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewModel.ScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ScreenViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme

            NotesAppTheme(darkTheme = isDarkTheme) {
                MainScreen(viewModel)
            }
        }
    }
}


