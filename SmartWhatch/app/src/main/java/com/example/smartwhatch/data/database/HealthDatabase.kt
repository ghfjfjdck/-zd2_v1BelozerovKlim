package com.example.smartwhatch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartwhatch.data.data.DataName
import com.example.smartwhatch.data.data.HealthData
import com.example.smartwhatch.data.data.User

@Database(entities = [User::class, HealthData::class, DataName::class], version = 1)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun healthDataDao(): HealthDataDao
    abstract fun dataNameDao(): DataNameDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null
        fun getDatabase(context: Context): HealthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthDatabase::class.java,
                    "health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}