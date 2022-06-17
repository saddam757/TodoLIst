package com.sh.todolist.data.repository

import com.sh.todolist.data.local.Todo
import com.sh.todolist.data.local.TodoDao
import kotlinx.coroutines.flow.Flow


interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo?

    fun getTodoList(): Flow<List<Todo>>
}

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)

    override suspend fun getTodoById(id: Int): Todo? = dao.getTodoById(id)

    override fun getTodoList(): Flow<List<Todo>> = dao.getTodoList()


}