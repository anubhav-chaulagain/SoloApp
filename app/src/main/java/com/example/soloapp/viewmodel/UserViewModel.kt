package com.example.soloapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.soloapp.model.UserModel
import com.example.soloapp.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository) {

    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    // (boolean, str) for success, message and userId
    fun signup(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.signup(email, password, callback)
    }

    // (boolean, str) for success and message
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun getCurrentUser() : FirebaseUser? {
        return repo.getCurrentUser()
    }

    var _userData = MutableLiveData<UserModel?>()
    var userData = MutableLiveData<UserModel?>()
        get() = _userData

    fun getDataFromDatabase(userId: String){
        repo.getDataFromDatabase(userId) {
                userModel, success, message->
            if(success){
                _userData.value = userModel
            } else {
                _userData.value = null
            }
        }
    }

    fun logout(callback: (Boolean, String)-> Unit){
        repo.logout(callback)
    }

    fun editProfile(userId: String, data: MutableMap<String, Any>, callback: (Boolean, String)->Unit){

    }
}