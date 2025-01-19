package com.example.animal.repo

import com.example.animal.model.Animal

class AnimalRepository {

    // List of Animals
    private val animals = mutableListOf<Animal>()

    // Get List
    fun getAllAnimals(): MutableList<Animal> {
        return animals
    }

    // Add new
    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

    // Edit
    fun updateAnimal(position: Int, animal: Animal) {
        if (position in animals.indices) { // Check position
            animals[position] = animal // update new animal
        } else {
            throw IndexOutOfBoundsException("Invalid position: $position") // Exception
        }
    }

    // Delete
    fun deleteAnimal(position: Int) {
        animals.removeAt(position)
    }
}