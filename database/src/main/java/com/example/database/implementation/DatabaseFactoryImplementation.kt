package com.example.database.implementation

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.DatabaseConfig
import com.example.database.DatabaseFactory
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Inject

class DatabaseFactoryImplementation @Inject constructor(
    private val context: Context
) : DatabaseFactory {

    override fun <TDatabase : RoomDatabase> createDatabase(config: DatabaseConfig<TDatabase>): TDatabase {
        val passphrase = SQLiteDatabase.getBytes(config.passphrase)
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, config.cls, config.name)
            .also { config.setup?.invoke(it) }
            .openHelperFactory(factory)
            .build()
    }
}
