package com.example.bingebox.ViewModel


import androidx.lifecycle.ViewModel
import com.example.bingebox.Model.MovieListModel
import com.example.bingebox.Repository.MovieRepository

class MovieViewModel : ViewModel() {
    // MovieRepository instance to handle data operations
    private val movieRepository = MovieRepository()

    // List to store the original set of movies
    private var originalMovies: List<MovieListModel> = emptyList()

    init {
        // Initialize the original list of movies
        originalMovies = getMovies()
    }

    // Method to get the list of movies from the repository
    fun getMovies() = movieRepository.getMovies()

    // Method to load more movies from the repository
    fun loadMoreMovies(onMoviesLoaded: (List<MovieListModel>) -> Unit) {
        movieRepository.loadMoreMovies(onMoviesLoaded)
    }

    // Method to search for movies based on a query string
    fun searchMovies(query: String, onMoviesFiltered: (List<MovieListModel>) -> Unit) {
        // Filter movies if the query length is 3 or more characters
        val filteredMovies = if (query.length >= 3) {
            originalMovies.filter { it.name.contains(query, ignoreCase = true) }
        } else {
            // Return the original list if the query is less than 3 characters
            originalMovies
        }
        // Callback with the filtered list of movies
        onMoviesFiltered(filteredMovies)
    }

    // Method to check if more movies are currently being loaded
    fun isLoading(): Boolean {
        return movieRepository.isLoading
    }

    // Method to check if the current page is the last page of movies
    fun isLastPage(): Boolean {
        return movieRepository.isLastPage
    }
}

