package com.example.facts.test.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.facts.database.Database
import com.example.facts.database.dao.FactsDao
import com.example.facts.model.Category
import com.example.facts.model.Fact
import com.example.typeconverter.DateTimeTypeConverter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FactsDaoTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dateTimeTypeConverter: DateTimeTypeConverter

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private val testTransactionDispatcher = TestCoroutineDispatcher()
    private val testTransactionScope = TestCoroutineScope(testTransactionDispatcher)

    private lateinit var db: Database
    private lateinit var dao: FactsDao


    @Before
    fun setup() {
        hiltRule.inject()

        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .setQueryExecutor(testDispatcher.asExecutor())
            .setTransactionExecutor(testTransactionDispatcher.asExecutor())
            .addTypeConverter(dateTimeTypeConverter)
            .build()

        dao = db.factsDao()
    }

    @Test
    fun insertAndRetrieveCategoryWithFacts() {
        val time = ZonedDateTime.now()
        val category = Category(name = "Test category", created = time)
        val fact = Fact(text = "Test fact")

        testScope.runBlockingTest {
            val catId = dao.insertCategory(category)
            assert(catId > 0L)
        }

        testTransactionScope.runBlockingTest {
            val factId = dao.insertFact(fact, listOf(category))
            assert(factId > 0L)
        }

//        testScope.runBlockingTest {
//
//            val categoryWithFacts = dao.getCategoryWithFacts(catId)
//
//            Assert.assertNotNull(categoryWithFacts)
//
//            categoryWithFacts!!.category.let {
//                Assert.assertEquals(catId, it.id)
//                Assert.assertEquals("Test category", it.name)
//                Assert.assertEquals(time, it.created)
//            }
//
//            Assert.assertEquals(1, categoryWithFacts.facts.size)
//
//            categoryWithFacts.facts.first().let {
//                Assert.assertEquals(factId, it.id)
//                Assert.assertEquals("Test fact", it.text)
//            }
//        }
    }
}