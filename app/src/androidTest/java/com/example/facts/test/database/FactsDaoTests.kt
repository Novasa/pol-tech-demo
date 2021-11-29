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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.xml.datatype.DatatypeConstants

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FactsDaoTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dateTimeTypeConverter: DateTimeTypeConverter

    private lateinit var db: Database
    private lateinit var dao: FactsDao


    @Before
    fun setup() {
        hiltRule.inject()

        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .addTypeConverter(dateTimeTypeConverter)
            .build()

        dao = db.factsDao()
    }

    @Test
    fun insertAndRetrieveCategoryWithFacts() {
        val time = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val category = Category(name = "Test category", created = time)
        val fact = Fact(text = "Test fact")

        runBlocking {
            val catId = dao.insertCategory(category)
            assertThat(catId, greaterThan(0))

            val dbCategory = dao.getCategory(catId)
            assertNotNull(dbCategory)
            dbCategory!!

            val factId = dao.insertFact(fact, listOf(dbCategory))
            assertThat(factId, greaterThan(0))

            val categoryWithFacts = dao.getCategoryWithFacts(catId)

            assertNotNull(categoryWithFacts)

            categoryWithFacts!!.category.let {
                assertEquals(catId, it.id)
                assertEquals("Test category", it.name)
                assert(it.created.isEqual(time))
            }

            assertEquals(1, categoryWithFacts.facts.size)

            categoryWithFacts.facts.first().let {
                assertEquals(factId, it.id)
                assertEquals("Test fact", it.text)
            }
        }
    }
}