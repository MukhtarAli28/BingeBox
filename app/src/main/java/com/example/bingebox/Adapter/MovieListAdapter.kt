package com.example.bingebox.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bingebox.Model.MovieListModel
import com.example.bingebox.R
import com.example.bingebox.databinding.XLayoutListingItemBinding
import com.squareup.picasso.Picasso

class MovieListAdapter(
    private val context: Context,
    private var movieList: MutableList<MovieListModel>, // Using MutableList to modify the list
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    // Method to create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val mBinding: XLayoutListingItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.x_layout_listing_item, parent, false
        )
        return ViewHolder(mBinding)
    }

    // Method to bind data to each ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    // Method to get the total number of items in the list
    override fun getItemCount(): Int {
        return movieList.size
    }

    // Method to update the movie list
    fun updateMovies(newMovies: List<MovieListModel>, isSearched: Boolean = false) {
        if (isSearched) {
            movieList.clear() // Clear the list if it's a search operation
        }
        val startPos = movieList.size
        movieList.addAll(newMovies)
        if (isSearched) {
            notifyDataSetChanged() // Notify adapter when it's a search operation
        } else {
            notifyItemRangeInserted(startPos, newMovies.size) // Notify adapter for new range of items
        }
    }

    // ViewHolder class to hold and bind the view elements
    inner class ViewHolder(private val binding: XLayoutListingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Bind the data to the view elements
        fun bind(movie: MovieListModel) {
            binding.tvTitle.text = movie.name

            // Get the resource id of the drawable by its name
            val resourceId = context.resources.getIdentifier(
                movie.posterImage.removeSuffix(".jpg"), "drawable", context.packageName
            )

            if (resourceId != 0) {
                // Create URI for the drawable resource
                val uri = Uri.parse("android.resource://${context.packageName}/$resourceId")
                // Load the image using Picasso library with placeholder and error handling
                Picasso.get().load(uri)
                    .placeholder(R.drawable.poster1) // Use a placeholder image
                    .into(binding.ivPoster)
            } else {
                // Load a default image when the resource ID is not found
                Picasso.get().load(R.drawable.ic_vector_loading)
                    .placeholder(R.drawable.ic_vector_loading) // Use a placeholder image
                    .error(R.drawable.ic_vector_loading) // Handle the case when image is not found
                    .fit() // Set scale type to fit center
                    .centerInside() // Use centerInside to fit the image inside the view
                    .into(binding.ivPoster)
            }


            // Set click listener on the parent layout
            binding.clParent.setOnClickListener {
                Toast.makeText(context, movie.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}



