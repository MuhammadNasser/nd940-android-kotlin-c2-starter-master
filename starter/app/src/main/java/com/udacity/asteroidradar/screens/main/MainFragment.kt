package com.udacity.asteroidradar.screens.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.screens.main.adapters.AsteroidsAdapter
import com.udacity.asteroidradar.viewmodels.MainViewModel
import com.udacity.asteroidradar.viewmodels.MainViewModelFactory

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            MainViewModelFactory(AsteroidDataBase.getInstance(application), application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidClickListener {
            viewModel.onAsteroidClicked(it)
        })

        with(binding) {
            mainViewModel = viewModel
            lifecycleOwner = this@MainFragment
            asteroidRecycler.adapter = adapter
        }

        with(viewModel) {
            asteroids.observe(viewLifecycleOwner) { asteroids ->
                asteroids?.let {
                    adapter.asteroidsList = it
                }
            }

            navigateToAsteroidDetails.observe(viewLifecycleOwner) { asteroid ->
                asteroid?.let {
                    findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                    onNavigatedToAsteroidDetails()
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> {
                viewModel.getWeekAsteroids()
            }
            R.id.show_today_menu -> {
                viewModel.getTodayAsteroids()
            }
            R.id.show_saved_menu -> {
                viewModel.getSavedAsteroids()
            }
        }
        return true
    }
}
