package by.brstu.redlabrat.testinternet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    var listAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.GONE

        val omdbApi = (application as TestApplication).omdbService

        val db = Room.databaseBuilder(applicationContext, SearchMovieDatabase::class.java, "search_movie.db")
            .fallbackToDestructiveMigration()
            .build()

        val movieDao = db.getMovieDao()

        buttonLoad.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            GlobalScope.launch {
                val result = omdbApi?.getMovieByPartName("test", "c8d2a36b")

                if (result != null) {
                    val listOfMovies = result.listOfMovies
                    if (listOfMovies != null) {
                        listAdapter?.listOfMovies = listOfMovies
                        val url = listOfMovies.first().posterUrl
                        movieDao.addMovies(listOfMovies)
                        if (url != null) {
                            loadImage(url)
                            withContext(Dispatchers.Main) {
                                listAdapter?.notifyDataSetChanged()
                                imageView.setImageBitmap(bitmap)
                                progressBar.visibility = View.GONE
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
        listAdapter = ListAdapter(applicationContext, emptyList())
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    fun loadImage(url: String) {
        val stream = URL(url).openStream()
        bitmap = BitmapFactory.decodeStream(stream)
    }
}
