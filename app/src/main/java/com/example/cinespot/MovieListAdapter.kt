package com.example.cinespot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinespot.database.MovieList

class MovieListAdapter(private var movies: List<MovieList>, private val onClick: (MovieList) -> Unit) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieNameTextView: TextView = itemView.findViewById(R.id.movieNameTextView)

        init {
            // Set the click listener on the item view to handle item clicks
            itemView.setOnClickListener {
                val movie = movies[adapterPosition]  // Get the movie at the clicked position
                onClick(movie)  // Invoke the onClick callback with the clicked movie
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val visitingCard = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MovieViewHolder(visitingCard)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieNameTextView.text = movie.movieName
    }

    override fun getItemCount(): Int = movies.size

    // Update the adapter with the new list of movies
    fun updateMovies(newMovies: List<MovieList>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}