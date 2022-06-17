package com.sh.todolist.ui.todo_list

import com.sh.todolist.data.local.Todo

sealed class TodoListEvent {
    data class OnClickTodo(val todo: Todo) : TodoListEvent()
    data class OnClickDeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnChangeIsdone(val todo: Todo, val isDone: Boolean) : TodoListEvent()
    object OnClickAddTodo : TodoListEvent()
    object OnClickUndoDeleteTodo : TodoListEvent()

}
