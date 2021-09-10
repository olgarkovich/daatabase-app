package com.example.databaseapp.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.databaseapp.databinding.ItemAnimalBinding
import com.example.databaseapp.model.Animal
import com.example.databaseapp.view.AnimalListener

class AnimalsAdapter(private val listener: AnimalListener) :
    ListAdapter<Animal, AnimalViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAnimalBinding.inflate(layoutInflater, parent, false)
        return AnimalViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }



    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Animal>() {

            override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.age == newItem.age && oldItem.breed == oldItem.breed
            }

            override fun getChangePayload(oldItem: Animal, newItem: Animal) = Any()
        }
    }
}