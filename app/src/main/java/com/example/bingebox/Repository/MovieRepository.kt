package com.example.bingebox.Repository

import com.example.bingebox.Model.MovieListModel
import org.json.JSONObject

class MovieRepository {

    private val movies = mutableListOf<MovieListModel>()
    var isLastPage = false
        private set
    var isLoading = false
        private set
    private var currentPage = 1

    fun getMovies(): List<MovieListModel> {
        return movies
    }

    fun loadMoreMovies(onMoviesLoaded: (List<MovieListModel>) -> Unit) {
        if (isLoading || isLastPage) return

        isLoading = true
        // Simulate API call
        val response = getApiResponse(currentPage)
        val jsonObject = JSONObject(response)
        val contentArray = jsonObject.getJSONObject("page").getJSONObject("content-items")
            .getJSONArray("content")

        for (i in 0 until contentArray.length()) {
            val item = contentArray.getJSONObject(i)
            val name = item.getString("name")
            val posterImage = item.getString("poster-image")
            movies.add(MovieListModel(name, posterImage))
        }

        onMoviesLoaded(movies)
        isLoading = false
        currentPage++

        // Check if this is the last page
        val totalContentItems = jsonObject.getJSONObject("page").getInt("total-content-items")
        if (movies.size >= totalContentItems) {
            isLastPage = true
        }
    }


    private fun getApiResponse(page: Int): String {
        // This function returns the JSON response based on the page number
        return when (page) {
            1 -> """
    {
  "page": {
    "title": "Romantic Comedy",
    "total-content-items" : "54",
    "page-num" : "1",
    "page-size" : "20",
    "content-items": {
      "content": [
        {
          "name": "The Birds",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster1.jpg"
        },
                {
          "name": "The Birds",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster3.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster2.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster1.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster1.jpg"
        }
      ]
    }
  }
}
""".trimIndent()

            2 -> """{
  "page": {
    "title": "Romantic Comedy",
    "total-content-items" : "54",
    "page-num" : "2",
    "page-size" : "20",
    "content-items": {
      "content": [
        {
          "name": "Rear Window",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster5.jpg"
        },
                {
          "name": "Rear Window",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster6.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster5.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster4.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster5.jpg"
        }
      ]
    }
  }
}""".trimIndent()

            3 -> """{
  "page": {
    "title": "Romantic Comedy",
    "total-content-items" : "27",
    "page-num" : "3",
    "page-size" : "14",
    "content-items": {
      "content": [
        {
          "name": "Family Pot",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster8.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster7.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster8.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster7.jpg"
        },
               {
          "name": "Family Pot",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "poster8.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster7.jpg"
        },
        {
          "name": "The Birds with an Extra Long Title",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "Rear Window",
          "poster-image": "poster9.jpg"
        },
        {
          "name": "The Birds",
          "poster-image": "poster8.jpg"
        },
        {
          "name": "Family Pot",
          "poster-image": "posterthatismissing.jpg"
        }
      ]
    }
  }
}"""

            else -> throw IllegalArgumentException("Invalid page number")
        }
    }
}
