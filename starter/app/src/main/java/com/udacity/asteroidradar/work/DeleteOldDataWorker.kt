package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.getDate

class DeleteOldDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "DeleteOldDataWorker"
    }

    override suspend fun doWork(): Result {
        val asteroidDataBase = AsteroidDataBase.getInstance(applicationContext)
        val asteroidRepo = AsteroidRepo(asteroidDataBase)

        return try {
            asteroidRepo.deleteOldAsteroidsData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}