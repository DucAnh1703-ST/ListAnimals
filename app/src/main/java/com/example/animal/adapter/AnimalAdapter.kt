package com.example.animal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animal.adapter.listener.IOnItemClickListener
import com.example.animal.databinding.ItemAnimalBinding
import com.example.animal.model.Animal

@SuppressLint("NotifyDataSetChanged")
class AnimalAdapter(
    private var animalList: MutableList<Animal>,
    private val listener: IOnItemClickListener,
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.bind(animal, position)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    fun updateData(newAnimalList: MutableList<Animal>) {
        animalList = newAnimalList
        notifyDataSetChanged()
    }

    // ViewHolder use ViewBinding
    inner class AnimalViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal, position: Int) {
            binding.txtName.text = animal.name
            binding.imageAnimal.setImageResource(animal.image)
            binding.imageColor.setImageResource(animal.color)
            binding.btnDelete.setOnClickListener { listener.onDeleteClick(position) }
            binding.root.setOnClickListener { listener.onItemClick(animal,position) }
        }
    }
}