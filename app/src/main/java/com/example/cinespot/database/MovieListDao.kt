package com.example.cinespot.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieListDao {
    @Insert
    suspend fun insert(movieList: MovieList)

    @Insert
    suspend fun insertAll(movieLists: List<MovieList>)  // Method to insert multiple items

    @Update
    suspend fun update(movieList: MovieList)

    @Delete
    suspend fun delete(movieList: MovieList)

    @Query("SELECT * from MovieList WHERE name = :movieName")
    suspend fun getMovieList(movieName: String): MovieList?

    @Query("SELECT * from MovieList ORDER BY name ASC")
    fun getMovieLists(): Flow<List<MovieList>>
}