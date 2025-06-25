package com.ketch.internal.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal object DatabaseInstance {
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            ALTER TABLE downloads 
            ADD COLUMN customNotificationTitle TEXT
        """.trimIndent()
            )
        }
    }

    @Volatile
    private var INSTANCE: DownloadDatabase? = null

    fun getInstance(context: Context): DownloadDatabase {
        if (INSTANCE == null) {
            synchronized(DownloadDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRoomDB(context)
                }
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DownloadDatabase::class.java,
            "ketch_downloader"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration().build()
}
