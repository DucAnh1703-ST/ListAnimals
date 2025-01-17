package com.example.animal.repo

import com.example.animal.model.Animal

class AnimalRepository {

    // Danh sách các con vật, giả sử đang lưu trong bộ nhớ (List)
    private val animals = mutableListOf<Animal>()

    // Lấy danh sách các con vật
    fun getAllAnimals(): MutableList<Animal> {
        return animals
    }

    // Thêm một con vật mới
    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

    // Sửa thông tin một con vật
    fun updateAnimal(animal: Animal) {
        val index = animals.indexOfFirst { it.name == animal.name }
        if (index != -1) {
            animals[index] = animal
        }
    }

    // Xóa một con vật
    fun deleteAnimal(position: Int) {
        animals.removeAt(position)
    }
}