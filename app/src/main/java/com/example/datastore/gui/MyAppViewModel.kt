package com.example.datastore.gui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.datastore.MyApplication
import com.example.datastore.data.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY as APPLICATION_KEY1

data class UiState(
    val userName: String,
    val userEmail: String
)

class MyAppViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val uiState: StateFlow<UiState> =
        userRepository.currentUser.map { user ->
            UiState(user.name, user.email)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState("Unknown", "Unknown")
        )

//    fun saveUserName(userName: String) {
//        viewModelScope.launch {
//            userRepository.saveUserName(userName)
//        }
//    }
//    fun saveUserEmail(userEmail: String) {
//        viewModelScope.launch {
//            userRepository.saveUserEmail(userEmail)
//        }
//
//    }

    fun saveUserNameEmail(userName: String, userEmail: String) {
        viewModelScope.launch {
            userRepository.saveUserNameEmail(userName, userEmail)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY1] as MyApplication)
                MyAppViewModel(application.userRepository)
            }
        }
    }

}
