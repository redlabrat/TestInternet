package by.brstu.redlabrat.testinternet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    fun addMovie(movie: SearchResultMovie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovies(movies: List<SearchResultMovie>)

    @Query("select * from movie_search")
    fun getAllMovies(): List<SearchResultMovie>
}