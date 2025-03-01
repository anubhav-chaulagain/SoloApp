package com.example.soloapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.soloapp.databinding.ActivityMainBinding
import com.example.soloapp.model.UserModel
import com.example.soloapp.repository.UserRepositoryImpl
import com.example.soloapp.viewmodel.UserViewModel
import com.example.soloapp.utils.LoadingUtil

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtil: LoadingUtil
    lateinit var UserModel: UserModel
    lateinit var UserRepositoryImpl: UserRepositoryImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gender = arrayOf("Male", "Female");

        val spinner = binding.genderSpinner;

        var adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item,
            gender
        )

        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner.onItemSelectedListener = this;
        spinner.adapter = adapter;

        binding.alreadyHaveAccText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        loadingUtil = LoadingUtil(this)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.btnRegister.setOnClickListener {
            loadingUtil.show()
            var email : String = binding.email.text.toString()
            var password : String = binding.password.text.toString()
            var username : String = binding.username.text.toString()
            var gender : String = binding.genderSpinner.selectedItem.toString()
            var address : String = binding.address.text.toString()

            userViewModel.signup(email, password) {
                    success, message, userId ->
                val userModel = UserModel(
                    userId,
                    email, username, gender, address
                )
                userViewModel.addUserToDatabase(userId, userModel) {
                        success, message ->
                    if(success) {
                        Toast.makeText(this@MainActivity,
                            "REGISTRATION SUCCESS", Toast.LENGTH_SHORT).show()
                        loadingUtil.dismiss();
                    } else {
                        Toast.makeText(this@MainActivity,
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

    fun onItemSelected(
        p0: AdapterView<*>?,
        p1: View?,
        p2: Int,
        p3: Long
    ) {
        return
    }

}

    override fun onItemSelected(
        p0: AdapterView<*>?,
        p1: View?,
        p2: Int,
        p3: Long
    ) {
        return
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun signup(){

    }

    fun addUser(userModel: UserModel){
        userViewModel.addUserToDatabase(userModel.userId,userModel){
                success,message ->
            if(success){
                Toast.makeText(this@MainActivity
                    ,message,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity
                    ,message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}