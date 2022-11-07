package au.kappabi.weatherapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.kappabi.weatherapp.database.WeatherRepository
import au.kappabi.weatherapp.network.WeatherApi


class HomeViewModelFactory (private val mWeatherApi: WeatherApi, private val mRepository: WeatherRepository,
                            private val mApplication: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mWeatherApi, mRepository, mApplication) as T
    }
}