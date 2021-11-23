package com.example.facts.database.query

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.facts.model.Category
import com.example.facts.model.Fact
import com.example.facts.database.relation.CategoryFactRelation


data class CategoryWithFacts(

    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CategoryFactRelation::class,
            parentColumn = "categoryId",
            entityColumn = "factId"
        )
    )
    val facts: List<Fact>
)
