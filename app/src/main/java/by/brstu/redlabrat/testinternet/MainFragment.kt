package by.brstu.redlabrat.testinternet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainFragment : Fragment(), ListAdapter.OnItemClickListener {

    var bitmap: Bitmap? = null
    var listAdapter: ListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.GONE

        val omdbApi = (activity?.application as TestApplication).omdbService

        val applicationContext = activity?.applicationContext!!
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
        listAdapter = ListAdapter(applicationContext, emptyList(), this)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL, false)
    }

    fun loadImage(url: String) {
        val stream = URL(url).openStream()
        bitmap = BitmapFactory.decodeStream(stream)
    }

    override fun onItemClick(movie: SearchResultMovie) {
        (activity as? MainActivity)?.onMovieInfoClick(movie)
    }
}