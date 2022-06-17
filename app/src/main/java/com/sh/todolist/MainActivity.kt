package com.sh.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sh.todolist.navigation.Routes
import com.sh.todolist.ui.add_edit_todo.AddEditTodoScreen
import com.sh.todolist.ui.theme.TodoLIstTheme
import com.sh.todolist.ui.todo_list.TodoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoLIstTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoListApp()
                }
            }
        }
    }
}

@Composable
fun TodoListApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.TODO_LIST) {

        composable(route = Routes.TODO_LIST) {
            TodoListScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
            arguments = listOf(
                navArgument(
                    name = "todoId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            AddEditTodoScreen(onPopBackStack = { navController.popBackStack() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoLIstTheme {
       TodoListApp()
    }
}