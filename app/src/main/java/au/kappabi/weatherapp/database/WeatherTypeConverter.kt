package au.kappabi.weatherapp.database

import androidx.room.TypeConverter
import au.kappabi.weatherapp.network.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class WeatherTypeConverter {
    @TypeConverter
    fun fromWeatherDataList(weatherList: List<WeatherData>?): String? {
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<WeatherData>?>() {}.getType()
        return gson.toJson(weatherList, type)
    }

    @TypeConverter
    fun toWeatherDataList(weatherList: String?): List<WeatherData>? {
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<WeatherData>?>() {}.getType()
        return gson.fromJson(weatherList, type)
    }
}
