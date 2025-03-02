package com.example.soloapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.soloapp.databinding.ActivityLoginBinding
import com.example.soloapp.utils.LoadingUtil
import com.example.soloapp.viewmodel.UserViewModel
import com.example.soloapp.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtil: LoadingUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createNewAccText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        loadingUtil = LoadingUtil(this)

        val userRepository = UserRepositoryImpl(FirebaseAuth.getInstance())
        userViewModel = UserViewModel(userRepository)

        binding.btnLogin.setOnClickListener {
            loadingUtil.show()
            var email : String = binding.editTextEmail.text.toString()
            var password : String = binding.editTextPassword.text.toString()

            userViewModel.login(email, password) {
                    success, message ->

                if(success) {
                    loadingUtil.dismiss()
                    val intent = Intent(this, ProductDashboardActivity::class.java)
                    startActivity(intent)

                } else {
                    loadingUtil.dismiss()
                    Toast.makeText(this@LoginActivity,
                        message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.textForgotPass.setOnClickListener {
            var email : String = binding.editTextEmail.text.toString()
            userViewModel.forgetPassword(email){
                    success, message ->

                if(success) {
                    loadingUtil.show();
                    Toast.makeText(this@LoginActivity,
                        "PASSWORD CHANGED", Toast.LENGTH_SHORT).show()
                    loadingUtil.dismiss();
                } else {
                    Toast.makeText(this@LoginActivity,
                        message, Toast.LENGTH_SHORT).show()
                    loadingUtil.dismiss()
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}