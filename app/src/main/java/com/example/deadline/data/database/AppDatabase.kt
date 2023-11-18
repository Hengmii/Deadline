package com.example.deadline.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Deadline::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun deadlineDao(): DeadlineDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "deadline_database"
                )
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE deadline ADD COLUMN startTime TEXT NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE deadline ADD COLUMN deadlineTime TEXT NOT NULL DEFAULT 0")

            }
        }
    }
}
