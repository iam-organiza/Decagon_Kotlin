package com.example.week9task.database.entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        Post::class,
        Comment::class
    ],
    version = 1,
)

abstract class BlogDB : RoomDatabase() {
    abstract val blogDao: BlogDao

    companion object {
        @Volatile
        private var INSTANCE: BlogDB? = null
        private val LOCK = Any()

        fun getInstance(context: Context): BlogDB {
            synchronized(LOCK) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BlogDB::class.java,
                    "blog_db"
                ).addCallback(rdc).build().also {
                    INSTANCE = it
                }
            }
        }

        var rdc: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                // ADD YOUR "Math - Sport - Art - Music" here
                println("created")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // do something every time database is open
                println("open")
            }
        }
    }
}
