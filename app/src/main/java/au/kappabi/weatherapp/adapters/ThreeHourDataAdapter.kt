package au.kappabi.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.weatherapp.R
import au.kappabi.weatherapp.network.WeatherData
import java.text.SimpleDateFormat

class ThreeHourDataAdapter : ListAdapter<WeatherData, ThreeHourDataAdapter.ThreeHourDataViewHolder>(ThreeHourDataDiffCallback) {

    class ThreeHourDataViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val timeTextView: TextView = v.findViewById(R.id.time_3hr_text_view)
        private val imageView : ImageView = v.findViewById(R.id.icon_3hr_image_view)

        fun bind(weatherData: WeatherData) {

            // Format time object
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(weatherData.dateTime)
            var timeText = ""
            val sdf_time = SimpleDateFormat("HH:mm a")
            timeText = sdf_time.format(date)
            timeTextView.text = timeText

            // TODO retreive the icon images

        }

    }

    object ThreeHourDataDiffCallback : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(
            oldItem: WeatherData,
            newItem: WeatherData
        ): Boolean {
            return oldItem.main == newItem.main
        }

        override fun areContentsTheSame(
            oldItem: WeatherData,
            newItem: WeatherData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ThreeHourDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeHourDataViewHolder {
        val vg = LayoutInflater.from(parent.context)
            .inflate(R.layout.three_hourly_item, parent, false)
        return ThreeHourDataViewHolder(vg)
    }
}