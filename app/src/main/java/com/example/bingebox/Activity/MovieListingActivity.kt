package com.example.bingebox.Activity

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingebox.Adapter.MovieListAdapter
import com.example.bingebox.Model.MovieListModel
import com.example.bingebox.R
import com.example.bingebox.ViewModel.MovieViewModel
import com.example.bingebox.databinding.ActivityMovieListingBinding

class MovieListingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMovieListingBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieListAdapter
    private var isSearching : Boolean = false // Flag to indicate if user is currently searching

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up the layout
        mBinding = ActivityMovieListingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Set up click listeners and adapter
        setClickListeners()
        adapter = MovieListAdapter(this, mutableListOf())
        mBinding.rvList.adapter = adapter

        // Set up RecyclerView layout manager
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 7 else 3
        mBinding.rvList.layoutManager = GridLayoutManager(this, spanCount)

        // Loading initial data
        loadMovies()

        // Setting scroll listener for lazy loading
        mBinding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                // Check if it's appropriate to load more movies
                if (!viewModel.isLoading() && !viewModel.isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMovies() // Load more movies
                    }
                }
            }
        })
    }

    // Function to set click listeners
    private fun setClickListeners() {
        // Click listener for back arrow
        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        // Click listener for search icon
        mBinding.ivSearch.setOnClickListener {
            if (mBinding.tvTitle.isVisible){
                mBinding.tvTitle.visibility=View.GONE
                mBinding.etSearch.visibility=View.VISIBLE
                mBinding.ivSearch.setImageResource(R.drawable.search_cancel)
                mBinding.etSearch.requestFocus()
                mBinding.etSearch.setSelection(mBinding.etSearch.text!!.length)

            }else{
                mBinding.tvTitle.visibility=View.VISIBLE
                mBinding.etSearch.visibility=View.GONE
                mBinding.ivSearch.setImageResource(R.drawable.search)
                isSearching = false
                viewModel.searchMovies("") { filteredMovies ->
                    adapter.updateMovies(filteredMovies, isSearched = true)
                }
            }
        }

        // Text watcher for search EditText
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // Perform search when text length is at least 3 characters
                val searchQuery = s.toString()
                if (searchQuery.length >= 3) {
                    isSearching = true // Set searching flag
                    viewModel.searchMovies(searchQuery) { filteredMovies ->
                        adapter.updateMovies(filteredMovies,isSearched = true) // Update adapter with filtered movies
                        setNoRecords(filteredMovies)

                    }
                }else{
                    isSearching = false
                    viewModel.searchMovies("") { filteredMovies ->
                        adapter.updateMovies(filteredMovies, isSearched = true)
                        setNoRecords(filteredMovies)

                    }
                }
            }
        })
    }

    // Function to load movies
    private fun loadMovies() {
        // If searching, directly load the next API response and perform search
        if (isSearching) {
            viewModel.loadMoreMovies { newMovies ->
                val searchQuery = mBinding.etSearch.text.toString()
                viewModel.searchMovies(searchQuery) { filteredMovies ->
                    adapter.updateMovies(filteredMovies, isSearched = true) // Update adapter with filtered movies
                    setNoRecords(filteredMovies)
                }
            }
        } else {
            // If not searching, simply load the next API response
            viewModel.loadMoreMovies { newMovies ->
                adapter.updateMovies(newMovies) // Update adapter with new movies
                setNoRecords(newMovies)

            }
        }
    }

    private fun setNoRecords(filteredMovies: List<MovieListModel>){
        if (filteredMovies.isEmpty()){
            mBinding.tvNoRecords.visibility = View.VISIBLE
            mBinding.rvList.visibility = View.GONE
        }else{
            mBinding.tvNoRecords.visibility = View.GONE
            mBinding.rvList.visibility = View.VISIBLE
        }
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}