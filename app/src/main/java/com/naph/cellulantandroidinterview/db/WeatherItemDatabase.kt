package com.naph.cellulantandroidinterview.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [WeatherItem::class],
    version = 1
)
abstract class WeatherItemDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}