package com.example.smartwhatch.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_names")
data class DataName (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)