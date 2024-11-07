import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rynnarriola.notetaking_app.R
import com.example.rynnarriola.notetaking_app.data.local.entity.NoteEntity
import com.example.rynnarriola.notetaking_app.ui.base.Screen
import com.example.rynnarriola.notetaking_app.ui.viewmodel.NotesViewModel

@Composable
fun PendingTaskScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddNoteScreen.route) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_note))
            }
        }
    ) { paddingValues -> // Add padding to avoid content overlap with the FAB
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between items
        ) {
            items(notes) { note ->
                NoteBox(
                    note = note,
                    onClick = {
                        navController.navigate("${Screen.EditNoteScreen.route}/${note.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun NoteBox(
    note: NoteEntity,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable(onClick = onClick) // Handle click to edit note
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
