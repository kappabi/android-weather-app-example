package au.kappabi.simpleweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.weatherapp.R
import au.kappabi.weatherapp.adapters.ThreeHourDataAdapter
import au.kappabi.weatherapp.network.WeatherData
import coil.load
import java.text.SimpleDateFormat

class WeatherDataAdapter : ListAdapter<List<WeatherData>, WeatherDataAdapter.WeatherViewHolder>(WeatherDataDiffCallback) {

    class WeatherViewHolder(v: View): RecyclerView.ViewHolder(v) {

        private val IMAGE_BASE_URL = "https://openweathermap.org/img/wn/"

        private val dateTextView : TextView = v.findViewById(R.id.date_text_view)
        private val timeTextView : TextView = v.findViewById(R.id.main_time_text_view)
        private val mainSummaryTextView : TextView = v.findViewById(R.id.main_summary_text_view)
        private val mainImageView : ImageView = v.findViewById(R.id.icon_main_image_view)
        private val threeHourRecyclerView : RecyclerView = v.findViewById(R.id.three_hour_recycler_view)
        private val cardView : CardView = v.findViewById(R.id.cardView)

        private val context = v.context
        private val adapter = ThreeHourDataAdapter()

        fun bind(weatherData: List<WeatherData>){

            // Format date object
            var dayText = ""
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(weatherData[0].dateTime)
            val sdf = SimpleDateFormat("dd MMM yyyy")
            dayText = sdf.format(date)
            dateTextView.text = dayText

            // Populate summary and time with first 3hr slot data
            mainSummaryTextView.text = weatherData[0].weather[0].summary
            var timeText = ""
            val sdf_time = SimpleDateFormat("h:mm a")
            timeText = sdf_time.format(date)
            timeTextView.text = timeText

            // Retreive the icon image // TODO Learn how to cache and reuse icons
            val imageName = weatherData[0].weather[0].icon
            val imageUri = IMAGE_BASE_URL + imageName + "@2x.png"
            mainImageView.load(imageUri)

            // Set up the three hour scrolling weather data views
            threeHourRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter.submitList(weatherData)
            threeHourRecyclerView.adapter = adapter

            // Navigate to details activity on card clicked
            cardView.setOnClickListener {
                //TODO Create details activity
            }

        }

    }

    object WeatherDataDiffCallback : DiffUtil.ItemCallback<List<WeatherData>>(){
        override fun areItemsTheSame(oldItem: List<WeatherData>, newItem: List<WeatherData>): Boolean {
            return oldItem[0].main == newItem[0].main
        }

        override fun areContentsTheSame(oldItem: List<WeatherData>, newItem: List<WeatherData>): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val vg = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_day_item, parent, false)
        return WeatherViewHolder(vg)
    }
}
