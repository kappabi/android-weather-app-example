package au.kappabi.weatherapp.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Base url for fetching weather data
private val BASE_URL = "https://samples.openweathermap.org/data/2.5/"

// Moshi object for converting json to weather data
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit object to build web service api
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    // Retrieve weather json from base url
    @GET("forecast?id=524901&appid=b6907d289e10d714a6e88b30761fae22")
    suspend fun getWeather(): WeatherResponse
}

// Public object to access the api service
object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
