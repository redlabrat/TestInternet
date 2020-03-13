package by.brstu.redlabrat.testinternet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    fun addMovie(movie: SearchResultMovie)

    @Insert
    fun addMovies(movies: List<SearchResultMovie>)

    @Query("select * from movie_search")
    fun getAllMovies(): List<SearchResultMovie>
}