package au.kappabi.weatherapp.database

import android.content.Context
import androidx.room.*

@Database(entities = [Weather::class], version = 1, exportSchema = false)
@TypeConverters(WeatherTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase =
            (
                    INSTANCE ?:
                    synchronized(this) {
                        val i = INSTANCE ?: Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "Weather_database"
                        ).build()
                        INSTANCE = i
                        INSTANCE
                    }
                    )!!
    }
}