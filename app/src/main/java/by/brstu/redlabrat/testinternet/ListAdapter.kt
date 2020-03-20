package by.brstu.redlabrat.testinternet

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ListAdapter(
    val context: Context,
    var listOfMovies: List<SearchResultMovie>,
    val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<ListAdapter.ViewCell>() {

    interface OnItemClickListener {
        fun onItemClick(movie: SearchResultMovie)
    }

    class ViewCell(cellView: View): RecyclerView.ViewHolder(cellView) {
        val titleText: TextView = cellView.findViewById(R.id.titleText)
        val titleYear: TextView = cellView.findViewById(R.id.yearText)
        val poster: ImageView = cellView.findViewById(R.id.imageViewPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCell {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewCell(view)
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: ViewCell, position: Int) {
        val movie = listOfMovies[position]
        holder.titleText.text = movie.title
        holder.titleYear.text = movie.year

        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(movie) }

        GlobalScope.launch {
            val stream = URL(movie.posterUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(stream)
            withContext(Dispatchers.Main) {
                holder.poster.setImageBitmap(bitmap)
            }
        }
    }


}