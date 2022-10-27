package au.kappabi.weatherapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import au.kappabi.weatherapp.network.WeatherData

@Entity(tableName = "weather")
class Weather {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "cod")
    var cod: Int = 0

    @ColumnInfo(name = "weather_list")
    var weatherList: List<WeatherData>? = null

}