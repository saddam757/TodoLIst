package com.sh.todolist

import android.app.Application
import com.sh.todolist.di.todoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TodoApplication)
            androidLogger()
            modules(todoModule)
        }
    }
}