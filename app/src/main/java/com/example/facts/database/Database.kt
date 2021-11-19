package com.example.facts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.facts.database.dao.FactsDao
import com.example.facts.model.Category
import com.example.facts.model.Fact
import com.example.facts.database.relation.CategoryFactRelation
import com.example.typeconverter.DateTimeTypeConverter

@Database(
    version = 2,
    entities = [
        Category::class,
        Fact::class,
        CategoryFactRelation::class
    ]
)
@TypeConverters(
    value = [
        DateTimeTypeConverter::class
    ]
)
abstract class Database : RoomDatabase() {

    companion object {
        const val NAME = "database"
    }

    abstract fun factsDao(): FactsDao
}