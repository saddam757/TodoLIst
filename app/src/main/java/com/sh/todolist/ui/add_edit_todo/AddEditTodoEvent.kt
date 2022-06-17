package com.sh.todolist.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnChangeTitle(val title: String) : AddEditTodoEvent()
    data class OnChangeDescription(val description: String) : AddEditTodoEvent()
    object OnClickSaveTodo : AddEditTodoEvent()
}
