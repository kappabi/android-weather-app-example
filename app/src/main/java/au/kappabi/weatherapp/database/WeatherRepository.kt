package au.kappabi.weatherapp.database

import android.app.Application
import androidx.lifecycle.LiveData

class WeatherRepository(application: Application) {
    private val weatherDao = AppDatabase.getDatabase(application).weatherDao()

    fun findByID(id: Int): LiveData<Weather> = weatherDao.findByID(id)

    fun save(weather: Weather) {
        weatherDao.save(weather)
    }

    fun deleteByID(id: Int){
        weatherDao.deleteByID(id)
    }

    fun removeAll() {
        weatherDao.removeAll()
    }

}