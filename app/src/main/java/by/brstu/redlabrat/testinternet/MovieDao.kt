package by.brstu.redlabrat.testinternet

import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    fun addMovie(movie: SearchResultMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<SearchResultMovie>)

    @Query("select * from movie_search")
    fun getAllMovies(): List<SearchResultMovie>
}