package com.example.facts.model.relation

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.facts.model.Category
import com.example.facts.model.Fact

@Entity(
    primaryKeys = [
        "categoryId",
        "factId"
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Fact::class,
            parentColumns = ["id"],
            childColumns = ["factId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class CategoryFactRelation(

    val categoryId: Long,
    val factId: Long
)
