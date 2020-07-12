package com.almaz.weather_aa.core

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.almaz.weather_aa.core.model.SavedLocation
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(savedLocation: SavedLocation) : Completable

    @Query("SELECT * FROM locations ORDER BY id DESC")
    fun getSavedLocations() : LiveData<List<SavedLocation>>
}
