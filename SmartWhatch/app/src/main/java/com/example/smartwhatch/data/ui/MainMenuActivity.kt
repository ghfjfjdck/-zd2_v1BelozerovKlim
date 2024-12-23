package com.example.smartwhatch.data.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartwhatch.data.data.DataName
import com.example.smartwhatch.data.database.HealthDatabase
import com.example.smartwhatch.data.database.SharedPreferencesManager
import com.example.smartwhatch.databinding.ActivityMainMenuBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var database: HealthDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        database = HealthDatabase.getDatabase(this)

        binding.inputDataButton.setOnClickListener {
            startActivity(Intent(this, DataInputActivity::class.java))
        }

        binding.viewDataButton.setOnClickListener {
            startActivity(Intent(this, DataListActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            sharedPreferencesManager.clearUser()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        initDataName()
    }

    private fun initDataName(){
        GlobalScope.launch(Dispatchers.IO) {
            val dataNameList = database.dataNameDao().getDataNameList()

            if (dataNameList.isEmpty()){
                database.dataNameDao().insert(DataName(name="Пульс"))
                database.dataNameDao().insert(DataName(name="Давление"))
                database.dataNameDao().insert(DataName(name="Сахар в крови"))
                database.dataNameDao().insert(DataName(name="Вес"))
            }
        }
    }
}