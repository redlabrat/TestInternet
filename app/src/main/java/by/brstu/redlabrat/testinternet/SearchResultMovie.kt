package by.brstu.redlabrat.testinternet

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_search")
data class SearchResultMovie(
    @ColumnInfo(name = "title")
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val posterUrl: String
)

/*
"Title":"Beta Test",
"Year":"2016",
"imdbID":"tt4244162",
"Type":"movie",
"Poster":"https://m.media-amazon.com/images/M/MV5BODdlMjU0MDYtMWQ1NC00YjFjLTgxMDQtNDYxNTg2ZjJjZDFiXkEyXkFqcGdeQXVyMTU2NTcxNDg@._V1_SX300.jpg"
 */