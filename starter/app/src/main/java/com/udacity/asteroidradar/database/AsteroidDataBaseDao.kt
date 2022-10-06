package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.models.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: Asteroid)

    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<Asteroid>>

    @Query("SELECT * FROM asteroids_table WHERE closeApproachDate >= :startDate AND closeApproachDate<= :endDate ORDER BY closeApproachDate ASC")
    fun getAllAsteroidsSortedByDate(startDate: String, endDate: String): Flow<List<Asteroid>>
}