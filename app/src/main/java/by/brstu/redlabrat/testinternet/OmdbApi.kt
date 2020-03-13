package by.brstu.redlabrat.testinternet

import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    suspend fun getMovieByPartName(@Query("s") partName: String,
                           @Query("apikey") apiKey: String): SearchResult
}