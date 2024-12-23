package com.example.smartwhatch.data.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smartwhatch.data.database.SharedPreferencesManager
import com.example.smartwhatch.data.data.User
import com.example.smartwhatch.data.database.HealthDatabase
import com.example.smartwhatch.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var database: HealthDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
var prov=1;
        sharedPreferencesManager = SharedPreferencesManager(this)
        database = HealthDatabase.getDatabase(this)

        binding.button.setOnClickListener {
            val username = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            lifecycleScope.launch(Dispatchers.IO) {
                val user: User? = database.userDao().getUserByLogin(username)
                withContext(Dispatchers.Main){
                    if(user != null){
                        sharedPreferencesManager.saveUser(user)
                        startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
                        finish()
                    } else {
                        prov=0;
                    }
                }
            }
            if(prov==0) {
                Toast.makeText(this, "Нету пользователя", Toast.LENGTH_SHORT).show()
            }
        }
        binding.registrationButton.setOnClickListener{

            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()

        }
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        val user = sharedPreferencesManager.getUser()
        if (user != null) {
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }
    }
}