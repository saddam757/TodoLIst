package com.sh.todolist.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sh.todolist.data.local.Todo
import com.sh.todolist.data.repository.TodoRepository
import com.sh.todolist.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditTodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: AddEditTodoEvent) {

        when (event) {

            is AddEditTodoEvent.OnChangeTitle -> {

                title = event.title
            }
            is AddEditTodoEvent.OnChangeDescription -> {
                description = event.description
            }
            is AddEditTodoEvent.OnClickSaveTodo -> {

                viewModelScope.launch {

                    if (title.isBlank()) {

                        SendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Title can't be empty"
                            )
                        )
                        return@launch
                    }

                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )

                    SendUiEvent(UiEvent.PopBackStack)
                }
            }
        }

    }

    private fun SendUiEvent(event: UiEvent) {

        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}