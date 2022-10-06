package com.udacity.asteroidradar.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.database.AsteroidDataBase

class MainViewModelFactory(
    private val dataBase: AsteroidDataBase,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataBase, application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}