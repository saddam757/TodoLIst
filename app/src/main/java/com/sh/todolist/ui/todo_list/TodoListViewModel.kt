package com.sh.todolist.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sh.todolist.data.local.Todo
import com.sh.todolist.data.repository.TodoRepository
import com.sh.todolist.navigation.Routes
import com.sh.todolist.util.UiEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val todo_List = repository.getTodoList()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {

        when (event) {

            is TodoListEvent.OnClickTodo -> {
                SendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId = ${event.todo.id}"))
            }
            is TodoListEvent.OnClickDeleteTodo -> {

                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    SendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Todo deleted",
                            actionLabel = "Undo"
                        )
                    )
                }

            }
            is TodoListEvent.OnChangeIsdone -> {

                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }

            }
            is TodoListEvent.OnClickAddTodo -> {

                SendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))

            }
            is TodoListEvent.OnClickUndoDeleteTodo -> {

                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
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