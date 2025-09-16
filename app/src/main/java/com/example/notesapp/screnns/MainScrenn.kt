package com.example.notesapp.screnns

import android.os.Build.VERSION_CODES.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Preview(showBackground = true , showSystemUi = true)
@Composable
fun MainScrenn() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        val texts = remember { mutableStateListOf(
            "Первый длинный текст для демонстрации...",
            "Второй длинный текст, который тоже можно развернуть.",
            "Третий текст с возможностью разворачивания."
        ) }
        val dialogState = remember{
            mutableStateOf(false)
        }
        val editIndex = remember {
            mutableStateOf<Int?>(null)
        }
        Column (
            modifier = Modifier.fillMaxSize().padding(top = 25.dp)
        ){

            if (dialogState.value){
                Dialog(
                    dialogState,
                    isEditing = editIndex.value != null,
                    editIndex.value?.let { texts[it] } ?: "",
                    onSubmit = {
                        if (editIndex.value != null) {
                            texts[editIndex.value!!] = it
                        } else {
                            texts.add(it)
                        }
                        editIndex.value = null
                    })
            }
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(texts.size) { index ->
                    MainCard(
                        text = texts[index],
                        deleteTexts = texts,
                        onEdit = {
                            editIndex.value = index
                            dialogState.value = true
                        }
                    )
                }
            }
        }
        IconButton(
            onClick = {
                editIndex.value = null
                dialogState.value = true
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
fun MainCard (text: String,deleteTexts: MutableList<String>,onEdit: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp)

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

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            TextButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Show less" else "Show more")
            }
           Row {
               IconButton(onClick = {
                   onEdit()
               }) {
                   Icon(
                       modifier = Modifier.padding(end = 0.dp),
                       imageVector = Icons.Filled.Edit,
                       contentDescription = "Edit",
                       tint = Color.Black
                   )
               }
               IconButton(onClick = {
                   deleteTexts.remove(text)
               }) {
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