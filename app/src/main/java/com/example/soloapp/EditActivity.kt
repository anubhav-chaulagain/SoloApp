package com.example.soloapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.soloapp.databinding.ActivityEditBinding
import com.example.soloapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""

        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

        // Load existing user data
        loadUserData()

        // Update user data when button is clicked
        binding.btnRegister.setOnClickListener {
            updateUserData()
        }
    }

    private fun loadUserData() {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                if (user != null) {
                    binding.username.setText(user.username)
                    binding.email.setText(user.email)
                    binding.address.setText(user.address)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUserData() {
        val newUsername = binding.username.text.toString().trim()
        val newEmail = binding.email.text.toString().trim()
        val newAddress = binding.address.text.toString().trim()

        if (newUsername.isEmpty() || newEmail.isEmpty() || newAddress.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedUser = mapOf(
            "username" to newUsername,
            "email" to newEmail,
            "address" to newAddress
        )

        databaseRef.updateChildren(updatedUser).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                finish() // Close activity after update

                startActivity(Intent(this@EditActivity, ProfileActivity::class.java));
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
