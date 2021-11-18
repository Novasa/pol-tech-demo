package com.example.database

import androidx.room.RoomDatabase

interface DatabaseFactory {

    fun <TDatabase : RoomDatabase> createDatabase(config: DatabaseConfig<TDatabase>) : TDatabase
}