package com.example.simpleapplogreg.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UserModel>>
    private val repository: UserRepository

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean>
        get() = _isLogin


    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userModel)
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLogin.postValue(repository.login(email, pass))
        }
    }
}