package au.kappabi.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE id = :arg LIMIT 1")
    fun findByID(arg: Int): LiveData<Weather>

    @Insert(onConflict = REPLACE)
    fun save(weather: Weather)

    @Query("DELETE FROM weather WHERE id= :id")
    fun deleteByID(id: Int)

    @Query("DELETE FROM weather")
    fun removeAll()

}