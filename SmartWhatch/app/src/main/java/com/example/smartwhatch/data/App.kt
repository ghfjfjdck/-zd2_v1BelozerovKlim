package com.example.smartwhatch.data

import android.app.Application
import com.example.smartwhatch.data.database.HealthDatabase

class App : Application(){
    companion object {
        lateinit var database: HealthDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = HealthDatabase.getDatabase(this)
    }
}