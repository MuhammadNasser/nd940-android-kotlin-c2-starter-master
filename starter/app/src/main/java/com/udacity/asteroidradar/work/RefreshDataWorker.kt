package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.getDate

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val asteroidDataBase = AsteroidDataBase.getInstance(applicationContext)
        val asteroidRepo = AsteroidRepo(asteroidDataBase)

        return try {
            asteroidRepo.refreshAsteroidData(getDate(), getDate(7))
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}