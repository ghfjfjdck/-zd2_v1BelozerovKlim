package com.example.smartwhatch.data.database

import androidx.annotation.NonNull
import androidx.room.*
import com.example.smartwhatch.data.data.User

@Dao
interface UserDao {
    @Insert
    @RewriteQueriesToDropUnusedColumns
    suspend fun insert( user: User): Unit

    @Update
    @RewriteQueriesToDropUnusedColumns
    suspend fun update( user: User): Unit

    @Delete
    @RewriteQueriesToDropUnusedColumns
    suspend fun delete( user: User): Unit

    @Query("SELECT * FROM users WHERE login = :login") // Изменен запрос
    suspend fun getUserByLogin(login: String): User?  // Изменен тип параметра и название метода
}