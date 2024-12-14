package com.example.cinespot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinespot.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import com.example.cinespot.database.MovieList
import com.example.cinespot.database.MovieListDao
import com.example.cinespot.database.MovieListRoomDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var dao: MovieListDao
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the MovieListDao
        dao = MovieListRoomDatabase.getDatabase(application).movieListDao()

        // Define the onClick listener for movie items
        val onClick: (MovieList) -> Unit = { movie ->
            // When a movie is clicked, navigate to the MovieReviewResult activity
            val intent = Intent(this@MainActivity, MovieReviewResult::class.java)
            intent.putExtra("MOVIE_NAME", movie.movieName)  // Pass the movie name to the result activity
            startActivity(intent)
        }

        // Initialize RecyclerView Adapter with the onClick listener
        movieListAdapter = MovieListAdapter(emptyList(), onClick)  // Initialize with empty list
        binding.movieListRecycView.layoutManager = LinearLayoutManager(this)
        binding.movieListRecycView.adapter = movieListAdapter

        // Fetch the movies from the database
        getMoviesFromDatabase()

        binding.addBtn.setOnClickListener {
            startMovieFormActivity()
        }
    }

    private fun getMoviesFromDatabase() {
        lifecycleScope.launch(Dispatchers.Main) {
            // Fetch the movies from the database
            val movies = dao.getMovieLists().first()  // Collects the list of movies from the Flow

            // Update the adapter with the fetched movies
            movieListAdapter.updateMovies(movies)

            // Notify the adapter that the data has changed
            movieListAdapter.notifyDataSetChanged()
        }
    }

    private fun startMovieFormActivity() {
        val movieReviewFormLinker = Intent(this, MovieReviewForm::class.java)
        startActivity(movieReviewFormLinker)
    }
}

// https://docs.google.com/spreadsheets/d/1pO9ev_bAYYe_FE5rTwKuE5PJaXtogCosZ6GZ0P2TjL4/edit?gid=0#gid=0
