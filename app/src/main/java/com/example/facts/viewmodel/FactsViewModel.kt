package com.example.facts.viewmodel

import androidx.lifecycle.*
import com.example.facts.model.Category
import com.example.facts.database.dao.FactsDao
import com.example.facts.model.Fact
import com.example.facts.model.query.CategoryWithFacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class FactsViewModel @Inject constructor(
    private val factsDao: FactsDao
) : ViewModel() {

    val categoryFlow: Flow<List<CategoryWithFacts>>
        get() = factsDao.categoryFlow()

    fun createCategory(name: String) {
        createCategory(Category(name = name, created = ZonedDateTime.now()))
    }

    fun createFact(text: String, categories: List<Category>) {
        createFact(Fact(text = text), categories)
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            factsDao.deleteCategory(category)
        }
    }

    private fun createCategory(category: Category) {
        viewModelScope.launch {
            factsDao.insertCategory(category)
        }
    }

    private fun createFact(fact: Fact, categories: List<Category>) {

    }
}
