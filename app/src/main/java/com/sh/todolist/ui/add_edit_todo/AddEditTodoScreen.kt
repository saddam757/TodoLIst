package com.sh.todolist.ui.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sh.todolist.util.UiEvent
import org.koin.androidx.compose.getViewModel

@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = getViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState, modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(AddEditTodoEvent.OnClickSaveTodo) }) {

                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")

            }
        }) {

        Column(modifier = Modifier.fillMaxSize()) {

            TextField(value = viewModel.title, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.OnChangeTitle(it))
            },
                placeholder = {
                    Text(text = "title...")
                })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.description, onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnChangeDescription(it))
                },
                placeholder = {
                    Text(text = "description...")
                },
                singleLine = false,
                maxLines = 5
            )

        }

    }

}