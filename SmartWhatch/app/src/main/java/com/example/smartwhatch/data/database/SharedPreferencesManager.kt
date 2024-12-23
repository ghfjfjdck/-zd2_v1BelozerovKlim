package com.example.smartwhatch.data.database

import android.content.Context
import android.content.SharedPreferences
import com.example.smartwhatch.data.data.User
import com.google.gson.Gson

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString(KEY_USER, userJson).apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return gson.fromJson(userJson, User::class.java)
    }

    fun clearUser() {
        sharedPreferences.edit().remove(KEY_USER).apply()
    }

    companion object {
        private const val PREF_NAME = "health_prefs"
        private const val KEY_USER = "user"
    }
}