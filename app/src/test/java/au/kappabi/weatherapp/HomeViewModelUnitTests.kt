package au.kappabi.simpleweatherapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import au.kappabi.weatherapp.database.WeatherRepository
import au.kappabi.weatherapp.viewmodels.HomeViewModel
import au.kappabi.weatherapp.network.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import java.lang.RuntimeException

/**
 * Local unit tests of the home view model.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class HomeViewModelUnitTests {
    // Test weather data
    private val testWeatherResponse = WeatherResponse(200, listOf(
        WeatherData("2017-01-30 18:00:00", WeatherMain(261.45f, 259.086f, 261.45f),
            listOf(WeatherWeather("Clear", "02n")), WeatherWind(4.77f)),
        WeatherData("2017-01-30 20:00:00", WeatherMain(261.45f, 259.086f, 261.45f),
            listOf(WeatherWeather("Clear", "02n")), WeatherWind(4.77f))))
    private val testWeatherResponse2Days = WeatherResponse(200, listOf(
        WeatherData("2017-01-30 18:00:00", WeatherMain(261.45f, 259.086f, 261.45f),
            listOf(WeatherWeather("Clear", "02n")), WeatherWind(4.77f)),
        WeatherData("2017-01-31 20:00:00", WeatherMain(261.45f, 259.086f, 261.45f),
            listOf(WeatherWeather("Clear", "02n")), WeatherWind(4.77f))))

    // Mock web retrieval service
    @Mock
    private lateinit var mockWeatherApi: WeatherApi
    @Mock
    private lateinit var mockRetrofitService: WeatherApiService
    @Mock
    private lateinit var mockContext: Application
    @Mock
    private lateinit var mockRepository: WeatherRepository

    // Needed to access the LiveData
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Set up the test dispatchers for testing of the coroutines
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialisation() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            whenever(mockRepository.findByID(any())).doReturn(MutableLiveData())

            val testViewModel = HomeViewModel(mockWeatherApi, mockRepository, mockContext)

            assert(testViewModel.loaded.value == true)
        }
    }

    @Test
    fun exceptionResponse() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doThrow(RuntimeException())
            whenever(mockRepository.findByID(any())).doReturn(MutableLiveData())

            val testViewModel = HomeViewModel(mockWeatherApi, mockRepository, mockContext)

            // Check loaded and empty list set
            assert(testViewModel.loaded.value == true)
            assert(testViewModel.fullWeatherList.value!!.isEmpty())
        }
    }

    @Test
    fun groupWeather() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            whenever(mockRepository.findByID(any())).doReturn(MutableLiveData())

            val testViewModel = HomeViewModel(mockWeatherApi, mockRepository, mockContext)

            // Check the two weather data has been grouped
            assert(testViewModel.groupedList.value!!.size == 1)
        }
    }

    @Test
    fun groupWeather2Days() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse2Days)
            whenever(mockRepository.findByID(any())).doReturn(MutableLiveData())

            val testViewModel = HomeViewModel(mockWeatherApi, mockRepository, mockContext)

            // Check the two weather data has been grouped
            assert(testViewModel.groupedList.value!!.size == 2)
        }
    }

}