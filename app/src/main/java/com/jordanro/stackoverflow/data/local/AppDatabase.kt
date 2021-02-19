package com.jordanro.stackoverflow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jordanro.stackoverflow.data.entities.Question

@Database(entities = [Question::class], version = 1, exportSchema = false)
@TypeConverters(BaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionsDao(): QuestionsDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
    }
}