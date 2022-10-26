package au.kappabi.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import au.kappabi.weatherapp.R
import coil.load
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

/**
 * Fragment to show weather forecast detail view
 */
class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from bundle
        val datetime = arguments?.getString("datetime")
        val summary = arguments?.getString("summary")
        val windspeed = arguments?.getFloat("wind")
        val currentTemp = arguments?.getFloat("currentTemp")
        val minTemp = arguments?.getFloat("minTemp")
        val maxTemp = arguments?.getFloat("maxTemp")
        val iconUri = arguments?.getString("iconUri")

        // Format date
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime)
        val sdf = SimpleDateFormat("h:mm a dd MMM yyyy")
        val datetimeText = sdf.format(date)

        // Populate views
        val datetimeTextView = getView()?.findViewById<TextView>(R.id.detail_date_text_view)
        datetimeTextView?.text = datetimeText
        val summaryTextView = getView()?.findViewById<TextView>(R.id.detail_summary_text_view)
        summaryTextView?.text = summary
        val windspeedTextView = getView()?.findViewById<TextView>(R.id.wind_text_view)
        windspeedTextView?.text = "${getString(R.string.wind)} ${windspeed?.roundToInt()}"
        val currentTempTextView = getView()?.findViewById<TextView>(R.id.curent_temp_text_view)
        currentTempTextView?.text = "${getString(R.string.current)} ${convertKelvinToCelsius(currentTemp)?.roundToInt()}${getString(R.string.degrees_C)}"
        val minTempTextView = getView()?.findViewById<TextView>(R.id.min_temp_text_view)
        minTempTextView?.text = "${getString(R.string.min)} ${convertKelvinToCelsius(minTemp)?.roundToInt()}${getString(R.string.degrees_C)}"
        val maxTempTextView = getView()?.findViewById<TextView>(R.id.max_temp_text_view)
        maxTempTextView?.text = "${getString(R.string.max)} ${convertKelvinToCelsius(maxTemp)?.roundToInt()}${getString(R.string.degrees_C)}"
        val iconImageView = getView()?.findViewById<ImageView>(R.id.icon_image_view)
        iconImageView?.load(iconUri)
    }

    private fun convertKelvinToCelsius(temp: Float?) : Float? {
        val result : Float?
        if (temp != null) {
            result = (temp - 273.15f)
        } else {
            result = null
        }
        return result
    }
}