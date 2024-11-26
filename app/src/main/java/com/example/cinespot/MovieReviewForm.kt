package com.example.cinespot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cinespot.database.MovieList
import com.example.cinespot.database.MovieListDao
import com.example.cinespot.database.MovieListRoomDatabase
import com.example.cinespot.databinding.ActivityMovieReviewFormBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieReviewForm : AppCompatActivity() {

    private lateinit var binding: ActivityMovieReviewFormBinding
    lateinit var mainViewModel:MainViewModel
    lateinit var dao: MovieListDao
    var TAG = MovieReviewForm::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_review_form)

        binding = ActivityMovieReviewFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        var database = MovieListRoomDatabase.getDatabase(this)
        dao = database.movieListDao()

        binding.backBtn.setOnClickListener {
            startHomeActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG,"im in onStart--getting data/location")

        binding.submitBtn.setOnClickListener {
            insertDataDb()
            startHomeActivity()
        }
    }

    private fun insertDataDb() {
        GlobalScope.launch {
            // Correct data retrieval from EditText and RatingBar
            val movieName = "\uD83C\uDFAC" + binding.aMovieNameText.text.toString()
            val movieGenre = binding.aGenreMsgText.text.toString()
            val movieRating = binding.aRatingBar.text.toString()  // RatingBar value(aRatingBar.rating.toString())
            val selectedRadioButtonId = binding.ratioGroupBtn.checkedRadioButtonId
            val movieRecommend = if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                selectedRadioButton.text.toString()  // Get the text ("Yes" or "No")
            } else {
                ""  // Default value if no radio button is selected
            }
            val movieMessages = binding.aThemeMovText.text.toString()
            val movieComments = binding.aCommentsText.text.toString()

            // Create the MovieList object
            val movieList = MovieList(
                movieName = movieName,
                movieGenre = movieGenre,
                movieRating = movieRating,
                movieRecommend = movieRecommend,
                movieMessages = movieMessages,
                movieComments = movieComments
            )

            // Insert the movie object into the database
            dao.insertAll(listOf(movieList))
        }
    }

    private fun startHomeActivity() {
        var homePageLinker = Intent(this,MainActivity::class.java)
        startActivity(homePageLinker)
    }

}
