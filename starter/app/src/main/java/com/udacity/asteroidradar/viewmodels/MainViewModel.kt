package com.udacity.asteroidradar.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.getDate
import com.udacity.asteroidradar.utils.isInternetAvailable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val dataBase: AsteroidDataBase, application: Application) :
    AndroidViewModel(application) {

    private val asteroidRepo = AsteroidRepo(dataBase)

    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails: LiveData<Asteroid>
        get() = _navigateToAsteroidDetails

    private val _asteroids = MutableLiveData<List<Asteroid>?>()
    val asteroids: LiveData<List<Asteroid>?>
        get() = _asteroids

    init {
        viewModelScope.launch {
            try {

//               ToDo: Udacity SUGGESTION: check the connectivity
                if (isInternetAvailable()) {
                    asteroidRepo.refreshAsteroidData(getDate(), getDate(7))
                    _pictureOfDay.value = asteroidRepo.loadPictureOfDay()
                } else {
                    Toast.makeText(
                        application,
                        "Please check your internet Connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(application, "Error: ${e.message}", Toast.LENGTH_SHORT).show()

                //     ToDo: Udacity SUGGESTION: use Timber in logs
                Timber.e(e.message.toString())
            }
        }
        getTodayAsteroids()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onNavigatedToAsteroidDetails() {
        _navigateToAsteroidDetails.value = null
    }

    fun getWeekAsteroids() {
        viewModelScope.launch {
            dataBase.asteroidDataBaseDao.getAllAsteroidsSortedByDate(getDate(), getDate(7))
                .collect {
                    _asteroids.value = it
                }
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            dataBase.asteroidDataBaseDao.getAllAsteroidsSortedByDate(getDate(), getDate())
                .collect {
                    _asteroids.value = it
                }
        }
    }

    fun getSavedAsteroids() {
        viewModelScope.launch {
            dataBase.asteroidDataBaseDao.getAllAsteroids().collect {
                _asteroids.value = it
            }
        }
    }
}