package com.example.smartwhatch.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smartwhatch.data.data.HealthData

@Dao
interface HealthDataDao {
    @Insert
    @RewriteQueriesToDropUnusedColumns
    suspend fun insert( healthData: HealthData): Unit

    @Update
    @RewriteQueriesToDropUnusedColumns
    suspend fun update( healthData: HealthData): Unit

    @Delete
    @RewriteQueriesToDropUnusedColumns
    suspend fun delete( healthData: HealthData): Unit

    @Query("SELECT * FROM health_data WHERE userId = :userId")
    @RewriteQueriesToDropUnusedColumns
    suspend fun getHealthDataList(userId: Long): List<HealthData>

}
