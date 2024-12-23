package com.example.smartwhatch.data.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smartwhatch.data.App
import com.example.smartwhatch.data.data.User
import com.example.smartwhatch.databinding.ActivityRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (login.isNotEmpty() && password.isNotEmpty()){
                lifecycleScope.launch {
                    App.database.userDao().insert(User(login=login, password=password))
                    showToast("Добавлено")
                }
            }else{
                showToast("Заполните все поля.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: AppCompatActivity) = android.content.Intent(context, RegistrationActivity::class.java)
    }
}