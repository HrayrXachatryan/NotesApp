package com.example.notesapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.notesapp.viewModel.ScreenViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MainScreen(viewModel: ScreenViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {


        if (viewModel.texts.isEmpty()) {
            EmptyStateScreen()
        } else {
            NotesList(viewModel)
        }


        IconButton(
            onClick = {
                viewModel.editIndex.value = null
                viewModel.dialogState.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(42.dp)
//                .shadow(5.dp, CircleShape)
                .background(Color.Black.copy(alpha = 0.7f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }


        if (viewModel.dialogState.value) {
            Dialog(
                dialogState = viewModel.dialogState,
                isEditing = viewModel.editIndex.value != null,
                initialText = viewModel.editIndex.value?.let { viewModel.texts[it] } ?: "",
                onSubmit = { newText ->
                    val editIndex = viewModel.editIndex.value
                    if (editIndex != null) {
                        viewModel.updateItem(editIndex, newText)
                        viewModel.editIndex.value = null
                    } else {
                        viewModel.addItem(newText)
                    }
                    viewModel.dialogState.value = false
                }
            )
        }
    }
}

@Composable
fun EmptyStateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.NoteAdd,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "You don’t have any notes yet",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the + icon in the bottom right corner to add one",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NotesList(viewModel: ScreenViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp, end = 8.dp, top = 25.dp, bottom = 8.dp
            )
    ) {
        items(viewModel.texts.size) { index ->
            MainCard(
                text = viewModel.texts[index],
                onEdit = {
                    viewModel.editIndex.value = index
                    viewModel.dialogState.value = true
                },
                onDelete = {
                    viewModel.removeItem(viewModel.texts[index])
                }
            )
        }
    }
}

@Composable
fun MainCard(
    text: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        val minimizedMaxLines = 1

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = text,
                maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Show less" else "Show more")
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}


