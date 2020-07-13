package com.almaz.weather_aa.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class SavedLocation(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "lat") var lat: String,
    @ColumnInfo(name = "lon") var lon: String
)