package com.jordanro.stackoverflow

import android.content.Context
import androidx.paging.DataSource
import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.data.entities.UserProfile
import com.jordanro.stackoverflow.data.local.AppDatabase
import com.jordanro.stackoverflow.data.local.QuestionsDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var userDao: QuestionsDao
    private lateinit var db: AppDatabase

    val baseCreatedAt :Long = 161381618
    val baseLastActivityAt :Long = 161381750
    val owner = UserProfile(1, "Owner", null, 1)

    val testQuestions = listOf(
        Question(1,"First",true,"",ArrayList(), owner,baseCreatedAt + 300,baseLastActivityAt),
        Question(2,"Second",true,"",ArrayList(), owner,baseCreatedAt - 150,baseLastActivityAt),
        Question(3,"Third",true,"",ArrayList(), owner,baseCreatedAt,baseLastActivityAt),
        Question(4,"Fourth",true,"",ArrayList(), owner,baseCreatedAt + 100,baseLastActivityAt),
        Question(5,"Fifth",true,"",ArrayList(), owner,baseCreatedAt - 50, baseLastActivityAt)
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.questionsDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testQuestionQuery() {
        assert(!isOrdered(testQuestions))
        runBlocking {
            userDao.insertAll(testQuestions)
            val orderedQuestion = toQuestionList(userDao.getQuestions())
            assert(orderedQuestion.size == testQuestions.size)
            assert(isOrdered(orderedQuestion))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testFilteredQuestionQuery() {
        assert(!isOrdered(testQuestions))
        runBlocking {
            userDao.insertAll(testQuestions)
            val orderedQuestion = toQuestionList(userDao.getAnsweredQuestions())
            assert(isOrdered(orderedQuestion))
            orderedQuestion.forEach {
                assert(it.isAnswered)
            }
        }
    }

    private fun toQuestionList(orderedQuestion: DataSource.Factory<Int, Question>): List<Question>{
        val loadRange = (orderedQuestion.create() as LimitOffsetDataSource).loadRange(0, 10)
        return loadRange
    }

    private fun isOrdered(questions: List<Question>): Boolean {
        return questions.asSequence().zipWithNext{a,b ->
            a.createdAt > b.createdAt
        }.all{it}
    }
}