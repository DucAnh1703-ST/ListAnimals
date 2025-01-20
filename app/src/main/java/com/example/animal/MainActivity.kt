package com.example.animal

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animal.adapter.AnimalAdapter
import com.example.animal.adapter.listener.IOnItemClickListener
import com.example.animal.databinding.ActivityMainBinding
import com.example.animal.model.Animal
import com.example.animal.viewmodel.AnimalViewModel

class MainActivity : AppCompatActivity(), IOnItemClickListener {

    private lateinit var animalAdapter: AnimalAdapter
    private lateinit var animalViewModel: AnimalViewModel

    // khai bao bien binding
    private lateinit var binding: ActivityMainBinding
    private var colorID: Int = -1
    private lateinit var selectedType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ViewModel
        animalViewModel = ViewModelProvider(this)[AnimalViewModel::class.java]

        //Adapter
        animalAdapter = AnimalAdapter(mutableListOf(), this)

        binding.recList.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = animalAdapter
        }

        animalViewModel.animals.observe(this) { animals ->
            // Observe of LiveData
            animalAdapter.updateData(animals)
        }

        val types = arrayOf(
            "Cat",
            "Dog",
            "Dragon",
            "Lion",
            "Shark",
            "Tiger"
        )

        // spinType
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinType.adapter = typeAdapter

        binding.spinType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val types2 = arrayOf(
            "Blue",
            "Pink",
            "Green",
            "Red",
            "Yellow",
            "Black"
        )

        // spinColor
        val typeAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, types2)
        typeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinColor.adapter = typeAdapter2

        binding.spinColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item
                val selectedColor = parent.getItemAtPosition(position).toString()
                colorID = animalViewModel.convertColorNameToId(selectedColor)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No items selected
            }
        }

        binding.btnAdd.setOnClickListener {
            if (binding.edtName.text.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.str_please_enter_a_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                addNewAnimal()
            }
        }

        binding.btnEdit.setOnClickListener {
            if (binding.edtName.text.isEmpty()) {
                Toast.makeText(this,
                    getString(R.string.toast_please_select_1_item), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNewAnimal() {
        val newName = binding.edtName.text.toString().trim()
        animalViewModel.addAnimal(selectedType, newName, colorID) // Call from ViewModel
        binding.edtName.setText("")
        Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(position: Int) {
        showConfirmDialog(position)
    }

    override fun onItemClick(animal: Animal, position: Int) {
        binding.edtName.setText(animal.name)
        val id1 = animal.color
        var color = 0

        when (id1) {
            R.drawable.blue -> color = 0
            R.drawable.pink -> color = 1
            R.drawable.green -> color = 2
            R.drawable.red -> color = 3
            R.drawable.yellow -> color = 4
            R.drawable.black -> color = 5
        }
        binding.spinColor.setSelection(color)

        val id2 = animal.type.toString()
        var animalID = 0
        when (id2) {
            "CAT" -> animalID = 0
            "DOG" -> animalID = 1
            "DRAGON" -> animalID = 2
            "LION" -> animalID = 3
            "SHARK" -> animalID = 4
            "TIGER" -> animalID = 5
        }
        binding.spinType.setSelection(animalID)

        binding.btnEdit.setOnClickListener {
            if (binding.edtName.text.isEmpty()) {
                Toast.makeText(this, R.string.toast_please_select_1_item, Toast.LENGTH_SHORT).show()
            } else {
                val selectedColorPosition = binding.spinColor.selectedItemPosition
                var newIDColor = 0
                when (selectedColorPosition) {
                    0 -> newIDColor = R.drawable.blue
                    1 -> newIDColor = R.drawable.pink
                    2 -> newIDColor = R.drawable.green
                    3 -> newIDColor = R.drawable.red
                    4 -> newIDColor = R.drawable.yellow
                    5 -> newIDColor = R.drawable.black
                }

                val selectedTypePosition = binding.spinType.selectedItemPosition
                var newType123 = "Cat"
                when (selectedTypePosition) {
                    0 -> newType123 = "Cat"
                    1 -> newType123 = "Dog"
                    2 -> newType123 = "Dragon"
                    3 -> newType123 = "Lion"
                    4 -> newType123 = "Shark"
                    5 -> newType123 = "Tiger"
                }

                val newName = binding.edtName.text.toString()
                animalViewModel.updateAnimal(newType123, newName, newIDColor, position)
                binding.edtName.setText("")
                Toast.makeText(this, "Edit success!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConfirmDialog(position: Int) {
        // Create AlertDialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete??")
            .setCancelable(false) // Choose yes or no
            .setPositiveButton("Yes") { _, _ ->
                animalViewModel.deleteAnimal(position)
                Toast.makeText(this, "Delete success!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Choose No
                dialog.dismiss() // Close dialog
            }
        // Display AlertDialog
        val alert = dialogBuilder.create()
        alert.show()
    }
}