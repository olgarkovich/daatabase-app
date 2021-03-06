package com.example.databaseapp.view.main

import androidx.recyclerview.widget.RecyclerView
import com.example.databaseapp.databinding.ItemAnimalBinding
import com.example.databaseapp.model.Animal
import com.example.databaseapp.view.AnimalListener

class AnimalViewHolder(
    private val binding: ItemAnimalBinding,
    private val listener: AnimalListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(animal: Animal) {
        binding.name.text = animal.name
        binding.age.text = animal.age
        binding.breed.text = animal.breed

        initButtonListeners(animal)
    }

    private fun initButtonListeners(animal: Animal) {
        binding.editButton.setOnClickListener {
            listener.update(animal)
        }
        binding.deleteButton.setOnClickListener {
            listener.delete(animal)
        }
    }
}