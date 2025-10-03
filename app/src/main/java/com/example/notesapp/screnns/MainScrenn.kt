package com.example.notesapp.screnns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notesapp.viewModel.ScreenViewModel


//@Preview(showBackground = true , showSystemUi = true)
@Composable
fun MainScrenn(viewModel: ScreenViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp)
        ) {
            if (viewModel.dialogState.value) {
                Dialog(
                    dialogState = viewModel.dialogState,
                    isEditing = viewModel.editIndex.value != null,
                    initialText = viewModel.editIndex.value?.let { viewModel.texts[it] } ?: "",
                    onSubmit = {
                        if (viewModel.editIndex.value != null) {
                            viewModel.texts[viewModel.editIndex.value!!] = it
                        } else {
                            viewModel.addItem(it)
                        }
                        viewModel.editIndex.value = null
                    }
                )
            }

            LazyColumn(modifier = Modifier.padding(8.dp)) {
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

        IconButton(
            onClick = {
                viewModel.editIndex.value = null
                viewModel.dialogState.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
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
        val minimizedMaxLines = 1
        var expanded by remember { mutableStateOf(false) }

        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 12.dp, top = 7.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
