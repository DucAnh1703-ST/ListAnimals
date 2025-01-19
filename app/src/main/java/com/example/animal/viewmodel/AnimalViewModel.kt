package com.example.animal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animal.convert.ColorConverter
import com.example.animal.factory.AnimalFactory
import com.example.animal.model.Animal
import com.example.animal.repo.AnimalRepository

class AnimalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AnimalRepository()

    // LiveData to contain the list
    private val _animals = MutableLiveData<MutableList<Animal>>()
    val animals: LiveData<MutableList<Animal>> = _animals

    // Get List from Repository
    private fun getAllAnimals() {
        _animals.value = repository.getAllAnimals()
    }

    // Add new
    fun addAnimal(type: String, name: String, color: Int) {
        val animal = AnimalFactory.createAnimal(type, name, color) // Factory logic here
        repository.addAnimal(animal)
        getAllAnimals()
    }

    // Update
    fun updateAnimal(type: String, name: String, color: Int, position: Int) {
        val animal = AnimalFactory.createAnimal(type, name, color) // Factory logic here
        repository.updateAnimal(position, animal)
        getAllAnimals()  // Update list after Update
    }

    // Delete
    fun deleteAnimal(position: Int) {
        repository.deleteAnimal(position)
        getAllAnimals()  // Update list after Delete
    }

    // Convert String to ID
    fun convertColorNameToId(colorName: String): Int? {
        return ColorConverter.colorNameToId(colorName)
    }
}