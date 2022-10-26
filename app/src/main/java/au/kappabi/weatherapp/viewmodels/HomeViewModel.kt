package au.kappabi.simpleweatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.*
import au.kappabi.weatherapp.network.WeatherApi
import au.kappabi.weatherapp.network.WeatherData
import kotlinx.coroutines.launch

class HomeViewModel(val weatherApi : WeatherApi) : ViewModel() {

    // Weather data as retrieved from the server
    private val _response = MutableLiveData<List<WeatherData>>()
    val fullWeatherList: LiveData<List<WeatherData>> = _response

    // Status of data retrieval to display feedback to user.
    private var _loaded = MutableLiveData<Boolean>(false)
    var loaded: LiveData<Boolean> = _loaded

    // Weather data grouped by date
    private var _groupedList = MutableLiveData<Map<String, List<WeatherData>>>()
    var groupedList : LiveData<Map<String, List<WeatherData>>> = _groupedList

    /**
     * Initialisation of weather data
     */
    init {
        getWeatherData()
    }

    /**
     * Function to retrieve weather data using retrofit api service
     */
    fun getWeatherData() {
        _loaded.value = false
        viewModelScope.launch {
            try {
                val response = weatherApi.retrofitService.getWeather()
                _response.value = response.list
                groupWeather()
                _loaded.value = true
            } catch (e: Exception) {
                // Setting list to empty triggers display of error message to user in fragment.
                Log.e("WEB RETRIEVAL", e.toString() + e.stackTraceToString())
                _response.value = emptyList()
                _loaded.value = true
            }
        }
    }

    /**
     * Function to group by date in weather response
     */
    fun groupWeather() {
        if (!fullWeatherList.value.isNullOrEmpty()) {
            val weather = fullWeatherList.value as MutableList
            val groupedWeather = weather.groupBy { it.dateTime.split(" ")[0] }
            _groupedList.value = groupedWeather
        }
    }

    // TODO Persist data
}