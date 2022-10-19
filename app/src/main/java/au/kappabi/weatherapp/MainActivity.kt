package au.kappabi.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.MenuProvider
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

        val loadingSpinner = findViewById<ProgressBar>(R.id.loadingSpinner)
        loadingSpinner?.visibility = View.VISIBLE

        homeViewModel.groupedList.observe(this) {
            // Populate the recycler view with the list of weather data grouped by date
            adapter.submitList(it.values.toList())
            recyclerView?.adapter = adapter
        }

        // Put a loading spinner on the page while waiting for the weather data
        homeViewModel.loaded.observe(this) {
            if (it == false) {
                loadingSpinner?.visibility = View.VISIBLE
            } else {
                loadingSpinner?.visibility = View.GONE
            }
        }

        // Add menu items without overriding methods in the Activity
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_refresh, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_item_refresh -> {
                        // Reload the weather data
                        homeViewModel.getWeatherData()
                        true
                    }
                    else -> true
                }
            }
        })

    }

}