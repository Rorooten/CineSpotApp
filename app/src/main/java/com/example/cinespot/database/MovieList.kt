package com.example.cinespot.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val movieName: String,
    val movieGenre: String,
    val movieRating: String,
    val movieRecommend: String,
    val movieMessages: String,
    val movieComments: String
)