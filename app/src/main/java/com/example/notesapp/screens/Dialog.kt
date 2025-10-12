package com.example.notesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Dialog (
    dialogState: MutableState<Boolean>,
    isEditing: Boolean = false,
    initialText: String = "",
    onSubmit: (String) -> Unit
){
    val dialogText = remember {mutableStateOf(initialText) }
    AlertDialog(

        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = (if (isEditing) "Edit text" else "Enter text"),
                    modifier = Modifier.padding(bottom = 5.dp)

                )
                TextField(value = dialogText.value, onValueChange = {
                    dialogText.value = it
                })
            }
        },
        text = {
//            Text(text = dialogText)
        },
        onDismissRequest = {
            dialogState.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit(dialogText.value)
                    dialogState.value = false
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialogState.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    )
}