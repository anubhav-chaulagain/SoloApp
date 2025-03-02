package com.example.soloapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.soloapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("users")

        loadUserProfile()

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, AddProductActivity::class.java))
        }
        binding.floatingDashboardBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ProductDashboardActivity::class.java))
        }
        binding.editProfile.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, EditActivity::class.java))
        }
        binding.logout.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val username = snapshot.child("username").getValue(String::class.java) ?: "N/A"
                        val email = snapshot.child("email").getValue(String::class.java) ?: "N/A"

                        // Bind data to UI
                        binding.profileName.text = username
                        binding.profileEmail.text = email
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseError", "Failed to load user data", error.toException())
                }
            })
        }
    }
}
