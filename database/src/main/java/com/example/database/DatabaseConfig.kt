package com.example.database

import androidx.room.RoomDatabase

class DatabaseConfig<TDatabase : RoomDatabase>(
    val cls: Class<TDatabase>,
    val name: String,
    val passphrase: CharArray,
    val setup: (RoomDatabase.Builder<TDatabase>.() -> Unit)? = null
)