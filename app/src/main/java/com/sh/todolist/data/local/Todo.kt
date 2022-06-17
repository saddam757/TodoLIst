package com.sh.todolist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Todo(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val description: String?,
    val isDone: Boolean
)

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id ")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * FROM Todo")
    fun getTodoList(): Flow<List<Todo>>
}

@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val dao: TodoDao

    companion object {

        const val DATABASE_NAME = "todo_db"
    }
}
