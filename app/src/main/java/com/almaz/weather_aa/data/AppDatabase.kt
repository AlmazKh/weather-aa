package com.almaz.weather_aa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.almaz.weather_aa.core.WeatherDAO
import com.almaz.weather_aa.core.model.db.SavedLocation

@Database(entities = [SavedLocation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDAO

    companion object {
        private const val DATABASE_NAME = "weather_aa.db"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(context).also { sInstance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}
