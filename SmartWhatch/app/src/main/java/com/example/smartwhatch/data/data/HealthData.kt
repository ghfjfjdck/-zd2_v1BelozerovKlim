package com.example.smartwhatch.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dataNameId: Long,
    val value: Double,
    val timestamp: Long,
    val userId: Long
)