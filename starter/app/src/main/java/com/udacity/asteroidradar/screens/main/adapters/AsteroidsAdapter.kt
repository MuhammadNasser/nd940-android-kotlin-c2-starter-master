package com.udacity.asteroidradar.screens.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemRecyclerBinding
import com.udacity.asteroidradar.models.Asteroid

class AsteroidsAdapter(private val onItemClickListener: AsteroidClickListener) :
    RecyclerView.Adapter<AsteroidsAdapter.ViewHolder>() {

    var asteroidsList = listOf<Asteroid>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = asteroidsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = asteroidsList[position]
        holder.bind(asteroid, onItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, onItemClickListener: AsteroidClickListener) {
            binding.asteroid = asteroid
            binding.clickListener = onItemClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecyclerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}