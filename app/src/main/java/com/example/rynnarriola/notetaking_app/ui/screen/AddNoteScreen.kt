import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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

@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") } // State for the title input
    var message by remember { mutableStateOf("") } // State for the message input

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_new_note),
            style = MaterialTheme.typography.headlineMedium
        )

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
            maxLines = 5 // Allow multiple lines for the message
        )

        // Save button
        Button(
            onClick = {
                // Create a new NoteEntity with the input values
                val newNote = NoteEntity(
                    title = title,
                    message = message
                )
                // Call ViewModel to add the note
                viewModel.addNote(newNote)
                // Navigate back to the PendingTaskScreen
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

