package com.example.roomdatabasecrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabasecrud.Dao.AppDatabase
import com.example.roomdatabasecrud.Repository.NoteRepository
import com.example.roomdatabasecrud.ViewModel.NoteViewModel
import com.example.roomdatabasecrud.ViewModel.NoteViewModelFactory
import com.example.roomdatabasecrud.Views.NoteScreen
import com.example.roomdatabasecrud.ui.theme.RoomDatabaseCrudTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(db.noteDao())
        val viewModelFactory = NoteViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        setContent {
            RoomDatabaseCrudTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoteScreen(viewModel)
                }
            }
        }
    }
}
