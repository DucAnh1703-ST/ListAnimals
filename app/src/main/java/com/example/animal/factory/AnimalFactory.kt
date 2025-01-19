package com.example.animal.factory

import com.example.animal.model.Animal
import com.example.animal.model.Cat
import com.example.animal.model.Dog
import com.example.animal.model.Dragon
import com.example.animal.model.Lion
import com.example.animal.model.Shark
import com.example.animal.model.Tiger

object AnimalFactory {
    fun createAnimal(type: String, name: String, color: Int): Animal {
        return when (type) {
            "Cat" -> Cat(name, color)
            "Dog" -> Dog(name, color)
            "Dragon" -> Dragon(name, color)
            "Lion" -> Lion(name, color)
            "Shark" -> Shark(name, color)
            "Tiger" -> Tiger(name, color)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}