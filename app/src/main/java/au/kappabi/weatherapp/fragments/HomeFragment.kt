package au.kappabi.weatherapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.weatherapp.R
import au.kappabi.weatherapp.adapters.WeatherDataAdapter
import au.kappabi.weatherapp.database.WeatherRepository
import au.kappabi.weatherapp.network.WeatherApi
import au.kappabi.weatherapp.viewmodels.HomeViewModel
import au.kappabi.weatherapp.viewmodels.HomeViewModelFactory


/**
 * Fragment to show weather forecast on home screen
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    val weatherApi = WeatherApi
    val homeViewModel : HomeViewModel by viewModels { HomeViewModelFactory(weatherApi,WeatherRepository(requireActivity().application), requireActivity().application) }
    val adapter = WeatherDataAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.main_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(getView()?.context)

        val loadingSpinner = getView()?.findViewById<ProgressBar>(R.id.loadingSpinner)
        loadingSpinner?.visibility = View.VISIBLE

        homeViewModel.groupedList.observe(viewLifecycleOwner) {
            // Populate the recycler view with the list of weather data grouped by date
            adapter.submitList(it.values.toList())
            recyclerView?.adapter = adapter
        }

        // Put a loading spinner on the page while waiting for the weather data
        homeViewModel.loaded.observe(viewLifecycleOwner) {
            if (it == false) {
                loadingSpinner?.visibility = View.VISIBLE
            } else {
                loadingSpinner?.visibility = View.GONE
            }
        }
    }

    // Set up the menu item in the app bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_refresh, menu)
    }

    // Handle selection of items in the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_refresh -> {
                // Reload the weather data
                homeViewModel.getWeatherData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}