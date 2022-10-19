package au.kappabi.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.simpleweatherapp.adapters.WeatherDataAdapter
import au.kappabi.simpleweatherapp.viewmodels.HomeViewModel
import au.kappabi.weatherapp.network.WeatherApi

class MainActivity : AppCompatActivity() {

    val weatherApi = WeatherApi
    val homeViewModel = HomeViewModel(weatherApi)
    val adapter = WeatherDataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)

        homeViewModel.groupedList.observe(this) {
            // Populate the recycler view with the list of weather data grouped by date
            adapter.submitList(it.values.toList())
            recyclerView?.adapter = adapter
        }

        // TODO Add the loading spinner

        // TODO Add refresh button in menu

    }
}