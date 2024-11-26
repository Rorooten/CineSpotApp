package com.example.cinespot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieList::class], version = 4, exportSchema = false)
abstract class MovieListRoomDatabase:RoomDatabase() {
    abstract fun movieListDao(): MovieListDao

    //companion = static
    companion object{
        private var INSTANCE: MovieListRoomDatabase? = null

        fun getDatabase(context : Context):MovieListRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    MovieListRoomDatabase::class.java,
                    "movieList_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
