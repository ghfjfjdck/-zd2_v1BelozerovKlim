package com.example.smartwhatch.data.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smartwhatch.databinding.ActivityDataInputBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.smartwhatch.data.database.HealthDatabase
import com.example.smartwhatch.data.data.DataName
import com.example.smartwhatch.data.data.HealthData
import com.example.smartwhatch.data.database.SharedPreferencesManager

class DataInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataInputBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var database: HealthDatabase
    private lateinit var dataNames: List<DataName>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        database = HealthDatabase.getDatabase(this)

        loadDataNames()

        binding.saveDataButton.setOnClickListener {
            saveHealthData()
        }
    }

    private fun loadDataNames() {
        lifecycleScope.launch(Dispatchers.IO) {
            dataNames = database.dataNameDao().getDataNameList()
            withContext(Dispatchers.Main) {
                setupSpinner()
            }
        }
    }
    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataNames.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dataNameSpinner.adapter = adapter
    }

    private fun saveHealthData() {
        val user = sharedPreferencesManager.getUser()
        if (user != null) {
            val selectedDataName = binding.dataNameSpinner.selectedItem as? String
            val value = binding.valueEditText.text.toString().toDoubleOrNull()
            if (selectedDataName != null && value != null) {
                val selectedDataNameId = dataNames.find { it.name == selectedDataName }?.id
                if (selectedDataNameId != null) {
                    val timestamp = System.currentTimeMillis()
                    val healthData = HealthData(dataNameId = selectedDataNameId, value = value, timestamp = timestamp, userId = user.id)
                    lifecycleScope.launch(Dispatchers.IO) {
                        database.healthDataDao().insert(healthData)
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@DataInputActivity, "Сохранено", Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}