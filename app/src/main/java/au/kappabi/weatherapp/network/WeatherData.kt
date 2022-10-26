package au.kappabi.weatherapp.network

import com.squareup.moshi.Json

data class WeatherResponse (

    val cod : Int,
    val list : List<WeatherData>

)

data class WeatherData (

    @Json(name = "dt_txt") val dateTime : String,
    val main : WeatherMain,
    val weather : List<WeatherWeather>,
    val wind : WeatherWind
)

data class WeatherMain (
    @Json(name = "temp") val temp : Float?,
    @Json(name = "temp_min") val minTemp : Float?,
    @Json(name = "temp_max") val maxTemp : Float?
)

data class WeatherWeather (
    @Json(name = "main") val summary : String?,
    @Json(name = "icon") val icon : String?
)

data class WeatherWind (
    @Json(name = "speed") val windspeed : Float?
)
