package com.example.smartwhatch.data.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartwhatch.data.adapters.DataListAdapter
import com.example.smartwhatch.databinding.ActivityDataListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.smartwhatch.data.data.HealthData
import com.example.smartwhatch.data.database.HealthDatabase
import com.example.smartwhatch.data.database.SharedPreferencesManager

class DataListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataListBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var database: HealthDatabase
    private lateinit var adapter: DataListAdapter
    private var healthDataList: List<HealthData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        database = HealthDatabase.getDatabase(this)

        binding.healthDataRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DataListAdapter(this, healthDataList)
        binding.healthDataRecyclerView.adapter = adapter
        loadHealthData()
    }

    private fun loadHealthData() {
        val user = sharedPreferencesManager.getUser()
        if (user != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                healthDataList = database.healthDataDao().getHealthDataList(user.id)
                withContext(Dispatchers.Main) {
                    adapter.updateData(healthDataList)
                }
            }
        }
    }
}