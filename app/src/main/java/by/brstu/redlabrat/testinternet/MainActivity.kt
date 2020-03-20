package by.brstu.redlabrat.testinternet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onMovieInfoClick(movie: SearchResultMovie) {
        val fragment = InfoFragment()
        val arguments = Bundle()
        arguments.putString(InfoFragment.IMAGE_URL_KEY, movie.posterUrl)
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .addToBackStack("InfoFragment")
            .commit()
    }
}
