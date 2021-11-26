package com.example.facts.database.dao

import androidx.room.*
import com.example.facts.model.Category
import com.example.facts.model.Fact
import com.example.facts.database.query.CategoryWithFacts
import com.example.facts.database.relation.CategoryFactRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface FactsDao {

    @Transaction
    @Query("SELECT * FROM Category")
    fun categoryFlow(): Flow<List<CategoryWithFacts>>

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Query("SELECT * FROM Category WHERE id = :id")
    suspend fun getCategory(id: Long): Category?

    @Query("SELECT * FROM Category WHERE id = :id")
    suspend fun getCategoryWithFacts(id: Long): CategoryWithFacts?

    @Transaction
    suspend fun insertFact(fact: Fact, categories: List<Category>): Long = insertFact(fact).also { factId ->
        insertCategoryFactRelations(categories.map { category -> CategoryFactRelation(category.id, factId) })
    }

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Insert
    suspend fun insertFact(fact: Fact): Long

    @Insert
    suspend fun insertCategoryFactRelations(relations: List<CategoryFactRelation>)

    @Delete
    suspend fun deleteCategory(category: Category)
}
