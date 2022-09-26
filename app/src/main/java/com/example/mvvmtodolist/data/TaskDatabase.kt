package com.example.mvvmtodolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mvvmtodolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

           val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Do the dishes"))
                dao.insert(Task("Exercise", important = true))
                dao.insert(Task("Coding", important = true))
                dao.insert(Task("Study"))
                dao.insert(Task("Clean the house", completed = true))
                dao.insert(Task("Buy Groceries"))

            }
        }
    }
}