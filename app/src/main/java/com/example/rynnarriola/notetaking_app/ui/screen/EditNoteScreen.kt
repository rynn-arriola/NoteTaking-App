package com.example.rynnarriola.notetaking_app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rynnarriola.notetaking_app.R
import com.example.rynnarriola.notetaking_app.data.local.entity.NoteEntity
import com.example.rynnarriola.notetaking_app.ui.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: Int, // Changed to Long for consistency with ID type
    viewModel: NotesViewModel = hiltViewModel()
) {
    val note by viewModel.getNoteById(noteId).collectAsState(initial = null)

    // Initialize title and message from the note or default to empty strings
    var title by remember { mutableStateOf(note?.title ?: "") }
    var message by remember { mutableStateOf(note?.message ?: "") }

    // Update state when the note data changes
    LaunchedEffect(note) {
        title = note?.title ?: ""
        message = note?.message ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.edit_note)) },
                actions = {
                    IconButton(onClick = {
                        note?.let {
                            viewModel.removeNote(it) // Remove the note
                            navController.popBackStack() // Navigate back after deletion
                        }
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete_note))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input for the title
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Input for the message
            TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text(stringResource(id = R.string.message)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            // Save button
            Button(
                onClick = {
                    val updatedNote = NoteEntity(
                        id = noteId, // Use the noteId to update the correct note
                        title = title,
                        message = message
                    )
                    viewModel.editNote(updatedNote)
                    navController.popBackStack() // Navigate back after saving
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}