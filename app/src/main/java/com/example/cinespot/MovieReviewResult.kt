package com.example.cinespot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cinespot.databinding.ActivityMovieReviewResultBinding
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.firstOrNull
import com.example.cinespot.database.MovieList
import com.example.cinespot.database.MovieListDao
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinespot.database.MovieListRoomDatabase


class MovieReviewResult : AppCompatActivity() {

    private lateinit var binding: ActivityMovieReviewResultBinding
    lateinit var dao: MovieListDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_review_result)

        binding = ActivityMovieReviewResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dao = MovieListRoomDatabase.getDatabase(application).movieListDao()

        val movieName = intent.getStringExtra("MOVIE_NAME") // Get movie name passed from MainActivity

        // Retrieve movie details from DB
        movieName?.let {
            getMovieDetails(it)
        }

        binding.backBtn.setOnClickListener {
            startHomeActivity()
        }
    }

    private fun getMovieDetails(movieName: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            // Collect the flow and find the movie
            dao.getMovieLists().collect { movieList ->
                val movie = movieList.firstOrNull { it.movieName == movieName }
                movie?.let {
                    binding.aMovieNameText.setText(it.movieName)
                    binding.aGenreMsgText.setText(it.movieGenre)
                    binding.aRatingBar.setText(it.movieRating)
                    binding.aRecommendText.setText(it.movieRecommend)
                    binding.aThemeMovText.setText(it.movieMessages)
                    binding.aCommentsText.setText(it.movieComments)
                }
            }
        }
    }

    private fun startHomeActivity() {
        var homePageLinker = Intent(this,MainActivity::class.java)
        startActivity(homePageLinker)
    }
}