package by.brstu.redlabrat.testinternet

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    val listOfMovies: List<SearchResultMovie>?,
    val totalResults: String?
)