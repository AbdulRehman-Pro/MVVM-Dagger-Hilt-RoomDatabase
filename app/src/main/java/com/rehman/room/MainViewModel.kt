package com.rehman.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehman.room.models.UserModel
import com.rehman.room.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val fetchUser: MutableStateFlow<ArrayList<UserModel>> = MutableStateFlow(ArrayList())


    fun insertUser(userModel: UserModel) = viewModelScope.launch {
        userRepository.insert(userModel)
    }

    fun getUsers() = viewModelScope.launch {
        userRepository.getUserData.flowOn(Dispatchers.IO).collect {
            fetchUser.value = it as ArrayList
        }
    }

    fun deleteUser(id: Int) = viewModelScope.launch {
        userRepository.deleteUser(id)
    }


}