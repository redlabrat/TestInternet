package by.brstu.redlabrat.testinternet

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchResultMovie::class], version = 1)
abstract class SearchMovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}