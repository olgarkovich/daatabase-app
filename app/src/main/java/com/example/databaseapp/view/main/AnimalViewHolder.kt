package com.example.databaseapp.view.main

import androidx.recyclerview.widget.RecyclerView
import com.example.databaseapp.databinding.ItemAnimalBinding
import com.example.databaseapp.model.Animal

class AnimalViewHolder(private val binding: ItemAnimalBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(animal: Animal) {
        binding.name.text = animal.name
        binding.age.text = animal.age
        binding.breed.text = animal.breed
    }


    init {
        binding.root.setOnClickListener {

        }
    }
}