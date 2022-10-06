package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.models.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {

    abstract val asteroidDataBaseDao: AsteroidDataBaseDao

    companion object {
        private lateinit var INSTANCE: AsteroidDataBase

        fun getInstance(context: Context): AsteroidDataBase {
            synchronized(AsteroidDataBase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDataBase::class.java,
                        "asteroids_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}