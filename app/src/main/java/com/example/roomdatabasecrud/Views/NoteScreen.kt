package com.example.roomdatabasecrud.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.roomdatabasecrud.Dao.Note
import com.example.roomdatabasecrud.Helper.EditNoteDialog
import com.example.roomdatabasecrud.ViewModel.NoteViewModel
import com.example.roomdatabasecrud.ui.theme.PurpleGrey80

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    val notes = viewModel.notes.value
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        if (!isEditing) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {})
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { })
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        viewModel.insert(Note(title = title, description = description))
                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Note")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn {
            items(notes) { note ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(PurpleGrey80)
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedNote = note
                                    title = note.title
                                    description = note.description
                                    isEditing = true
                                    isDialogOpen = true
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (isDialogOpen && selectedNote != null) {
        EditNoteDialog(
            note = selectedNote!!,
            title = title,
            description = description,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
            onUpdate = {
                viewModel.update(selectedNote!!.copy(title = title, description = description))
                isDialogOpen = false
                isEditing = false
                title = ""
                description = ""
            },
            onDelete = {
                viewModel.delete(selectedNote!!)
                isDialogOpen = false
                isEditing = false
                title = ""
                description = ""
            },
            onDismiss = {
                isDialogOpen = false
                isEditing = false
                title = ""
                description = ""
            }
        )
    }
}
