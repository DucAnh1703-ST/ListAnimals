package com.example.animal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animal.adapter.AnimalAdapter
import com.example.animal.adapter.listener.IOnItemClickListener
import com.example.animal.databinding.ActivityMainBinding
import com.example.animal.model.Animal
import com.example.animal.model.Cat
import com.example.animal.model.Dog
import com.example.animal.model.Dragon
import com.example.animal.model.Shark
import com.example.animal.model.Lion
import com.example.animal.model.Tiger
import com.example.animal.model.Species
import com.example.animal.viewmodel.AnimalViewModel

class MainActivity : AppCompatActivity(), IOnItemClickListener {

    private lateinit var animalAdapter: AnimalAdapter
    private lateinit var animalViewModel: AnimalViewModel
    // khai bao bien binding
    private lateinit var binding: ActivityMainBinding
    private var colorID : Int = -1
    private lateinit var selectedType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // thiet lap binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //khởi tạo ViewModel
        animalViewModel = ViewModelProvider(this)[AnimalViewModel::class.java]

        //khởi tạo adpater cho listview
        animalAdapter = AnimalAdapter(mutableListOf(), this)

        binding.recList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = animalAdapter
        }

        animalViewModel.animals.observe(this, Observer { animals ->
            // Khi LiveData thay đổi, cập nhật danh sách trong Adapter
            animalAdapter.updateData(animals)
        })

//        val newAnimal1 = Dog("Animal",R.drawable.blue)
//        animalViewModel.addAnimal(newAnimal1)
//        animalViewModel.addAnimal(newAnimal1)
//        animalViewModel.addAnimal(newAnimal1)

        val types = arrayOf(
            "Cat",
            "Dog",
            "Dragon",
            "Lion",
            "Shark",
            "Tiger")

        // Tạo ArrayAdapter cho Spinner Type
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinType.adapter = typeAdapter

        binding.spinType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedType = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, "Bạn đã chọn: $selectedType", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không làm gì
            }
        }

        val types2 = arrayOf(
            "Blue",
            "Pink",
            "Green",
            "Red",
            "Yellow",
            "Black")

        // Tạo ArrayAdapter cho Spinner Type
        val typeAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, types2)
        typeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinColor.adapter = typeAdapter2

        binding.spinColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Lấy item đã chọn
                val selectedColor = parent.getItemAtPosition(position).toString()
                when(selectedColor){
                    "Blue" -> colorID = R.drawable.blue
                    "Pink" -> colorID = R.drawable.pink
                    "Green" -> colorID = R.drawable.green
                    "Red" -> colorID = R.drawable.red
                    "Yellow" -> colorID = R.drawable.yellow
                    "Black" -> colorID = R.drawable.black
                }
                // Thực hiện hành động với item đã chọn (in ra màu)
//                Toast.makeText(this@MainActivity, "$colorID", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Không có item nào được chọn
            }
        }

        binding.btnAdd.setOnClickListener {
            if(binding.edtName.text.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show()
            }else{
                addNewAnimal()
            }
        }
    }

    private fun addNewAnimal(){
        val newName = binding.edtName.text.toString().trim()
        animalViewModel.addAnimal(selectedType, newName, colorID) // Gọi hàm từ ViewModel
        binding.edtName.setText("")
    }

    override fun onDeleteClick(position: Int) {
        showConfirmDialog(position)
    }

    override fun onItemClick(animal: Animal) {
        binding.edtName.setText(animal.name)
        val id1 = animal.color
        var color : Int = 0
        when(id1){
            R.drawable.blue -> color = 0
            R.drawable.pink -> color = 1
            R.drawable.green -> color = 2
            R.drawable.red -> color = 3
            R.drawable.yellow -> color = 4
            R.drawable.black -> color = 5
        }
        binding.spinColor.setSelection(color)

        val id2 = animal.type.toString()
        var animalID : Int = 0
        when(id2){
            "CAT" -> animalID = 0
            "DOG" -> animalID = 1
            "DRAGON" -> animalID = 2
            "LION" -> animalID = 3
            "SHARK" -> animalID = 4
            "TIGER" -> animalID = 5
        }
        binding.spinType.setSelection(animalID)

        binding.btnEdit.setOnClickListener {
            if(binding.edtName.text.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show()
            }else{
                editAnimal();
            }
        }
    }

    private fun editAnimal(){

    }

    private fun showConfirmDialog(position: Int) {
        // Tạo AlertDialog để xác nhận xóa
        val dialogBuilder = AlertDialog.Builder(this)
            .setMessage("Bạn có chắc muốn xóa không?")
            .setCancelable(false) // Không thể hủy dialog ngoài việc chọn Có hoặc Không
            .setPositiveButton("Có") { dialog, id ->
                animalViewModel.deleteAnimal(position)
                Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Không") { dialog, id ->
                // Xử lý khi người dùng chọn "Không"
                dialog.dismiss() // Đóng dialog
            }
        // Hiển thị AlertDialog
        val alert = dialogBuilder.create()
        alert.show()
    }
}