package com.example.soloapp.repository

import com.example.soloapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    // (boolean, str) for success and message
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)

    // (boolean, str) for success, message and userId
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit)

    // (boolean, str) for success and message
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)

    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit)

    fun getCurrentUser() : FirebaseUser?

    fun getDataFromDatabase(userId: String, callback: (UserModel?, Boolean, String) -> Unit)

    fun logout(callback: (Boolean, String)-> Unit)

    fun editProfile(userId: String, data: MutableMap<String, Any>, callback: (Boolean, String)->Unit)
}
