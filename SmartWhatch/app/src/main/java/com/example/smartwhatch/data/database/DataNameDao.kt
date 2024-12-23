package com.example.smartwhatch.data.database


import androidx.room.*
import com.example.smartwhatch.data.data.DataName
import androidx.annotation.NonNull // Import NonNull annotation

@Dao
interface DataNameDao {
    @Insert
    @RewriteQueriesToDropUnusedColumns
    suspend fun insert( dataName: DataName): Unit

    @Update
    @RewriteQueriesToDropUnusedColumns
    suspend fun update( dataName: DataName): Unit

    @Delete
    @RewriteQueriesToDropUnusedColumns
    suspend fun delete(  dataName: DataName): Unit

    @Query("SELECT * FROM data_names")
    @RewriteQueriesToDropUnusedColumns
    suspend fun getDataNameList(): List<DataName>
}