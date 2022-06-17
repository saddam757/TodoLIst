package com.sh.todolist.di
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.sh.todolist.data.local.TodoDatabase
import com.sh.todolist.data.repository.TodoRepository
import com.sh.todolist.data.repository.TodoRepositoryImpl
import com.sh.todolist.ui.add_edit_todo.AddEditTodoViewModel
import com.sh.todolist.ui.todo_list.TodoListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.emptyParametersHolder
import org.koin.dsl.module

val todoModule = module {

    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }
    single<TodoRepository> {
        TodoRepositoryImpl(get<TodoDatabase>().dao)
    }
    viewModel {
        TodoListViewModel(get())
    }
    viewModel {
        AddEditTodoViewModel(get())
    }

}