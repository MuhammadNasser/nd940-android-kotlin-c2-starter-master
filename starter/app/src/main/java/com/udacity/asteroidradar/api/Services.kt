package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.models.PictureOfDay
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
        endDate: String
    ): Deferred<ResponseBody>


    @GET("planetary/apod")
    fun loadPictureOfDayAsync(): Deferred<PictureOfDay>
}