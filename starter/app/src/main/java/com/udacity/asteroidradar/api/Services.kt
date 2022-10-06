package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("neo/rest/v1/feed")
    fun getNearEarthObjectsAsync(
        @Query("start_date")
        startDate: String,
        @Query("end_date")
        endDate: String,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Deferred<ResponseBody>


    @GET("planetary/apod")
    fun loadPictureOfDayAsync(
        @Query("api_key")
        apiKey: String = API_KEY
    ):Deferred<PictureOfDay>
}