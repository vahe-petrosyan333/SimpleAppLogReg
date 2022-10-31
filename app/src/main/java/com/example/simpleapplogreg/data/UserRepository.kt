package com.example.simpleapplogreg.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<UserModel>> = userDao.readAllData()

    fun addUser(userModel: UserModel) {
        userDao.addUser(userModel)
    }

    fun login(email: String, pass: String): Boolean {
       return userDao.login(email, pass)
    }
}