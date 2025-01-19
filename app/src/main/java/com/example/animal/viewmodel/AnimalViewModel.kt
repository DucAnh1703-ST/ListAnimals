package com.example.animal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animal.factory.AnimalFactory
import com.example.animal.model.Animal
import com.example.animal.repo.AnimalRepository

class AnimalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AnimalRepository()

    // LiveData để chứa danh sách các con vật
    private val _animals = MutableLiveData<MutableList<Animal>>()
    val animals: LiveData<MutableList<Animal>> = _animals

    // Lấy danh sách động vật từ Repository
    private fun getAllAnimals() {
        _animals.value = repository.getAllAnimals()
    }

    // Thêm một con vật mới
    fun addAnimal(type: String, name: String, color: Int) {
        val animal = AnimalFactory.createAnimal(type, name, color) // Factory logic ở đây
        repository.addAnimal(animal)
        getAllAnimals()
    }

    // Cập nhật thông tin của con vật
    fun updateAnimal(animal: Animal) {
        repository.updateAnimal(animal)
        getAllAnimals()  // Cập nhật danh sách sau khi sửa
    }

    // Xóa một con vật
    fun deleteAnimal(position: Int) {
        repository.deleteAnimal(position)
        getAllAnimals()  // Cập nhật danh sách sau khi xóa
    }
}