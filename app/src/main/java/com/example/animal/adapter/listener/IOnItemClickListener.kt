package com.example.animal.adapter.listener

import com.example.animal.model.Animal

interface IOnItemClickListener {
    fun onDeleteClick(position: Int)
    fun onItemClick(animal: Animal, position: Int)
}