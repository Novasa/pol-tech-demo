package com.example.facts.viewmodel

import androidx.lifecycle.*
import com.example.facts.model.Category
import com.example.facts.database.dao.FactsDao
import com.example.facts.model.Fact
import com.example.facts.database.query.CategoryWithFacts
import com.example.utility.state.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val selectedCategories = HashSet<Category>()
    private val _createFactStateFlow = MutableStateFlow<Data.State>(Data.Empty)
    val createFactStateFlow = _createFactStateFlow.asStateFlow()

    fun setCategorySelected(category: Category, selected: Boolean) {
        if (selected) {
            selectedCategories.add(category)
        } else {
            selectedCategories.remove(category)
        }
    }

    fun createFact(text: String) {
        _createFactStateFlow.value = Data.Progress
        viewModelScope.launch {
            factsDao.createFact(Fact(text = text), selectedCategories.toList())
            _createFactStateFlow.value = Data.Complete

            selectedCategories.clear()
            _createFactStateFlow.value = Data.Empty
        }
    }
}
