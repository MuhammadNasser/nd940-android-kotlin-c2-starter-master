package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.api.RetrofitClient
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.utils.asDatabaseModel
import com.udacity.asteroidradar.utils.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepo(private val dataBase: AsteroidDataBase) {

    suspend fun refreshAsteroidData(
        startDate: String,
        endDate: String
    ) {
        withContext(Dispatchers.IO) {
            val responseBody =
                RetrofitClient.retrofitService.getNearEarthObjectsAsync(startDate, endDate).await()

            val asteroids = parseAsteroidsJsonResult(JSONObject(responseBody.string()))
            dataBase.asteroidDataBaseDao.insertAll(*asteroids.asDatabaseModel())
        }
    }

    suspend fun loadPictureOfDay(): PictureOfDay? {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay =
                RetrofitClient.retrofitService.loadPictureOfDayAsync().await()
        }

        return if (pictureOfDay.mediaType == "image") {
            pictureOfDay
        } else {
            null
        }
    }
}