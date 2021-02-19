package com.jordanro.stackoverflow.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jordanro.stackoverflow.data.entities.Question

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questions ORDER BY createdAt DESC")
    fun getQuestions() : DataSource.Factory<Int,Question>

    @Query("SELECT * FROM questions where isAnswered = :answered ORDER BY createdAt DESC")
    fun getAnsweredQuestions(answered: Boolean = true) : DataSource.Factory<Int,Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}