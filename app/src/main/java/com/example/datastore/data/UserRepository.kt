package com.example.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }

    val currentUserName: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: "Unknown"
        }

    val currentUserEmail: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_EMAIL] ?: "Unknown"
        }

    val currentUser: Flow<com.example.datastore.data.User> =
        combine(currentUserName, currentUserEmail) { userName, userEmail ->
            User(userName, userEmail)
        }

//    suspend fun saveUserName(userName: String) {
//        dataStore.edit { preferences ->
//            preferences[USER_NAME] = userName
//        }
//    }
//
//    suspend fun saveUserEmail(userEmail: String) {
//        dataStore.edit { preferences ->
//            preferences[USER_EMAIL] = userEmail
//        }
//    }

    suspend fun saveUserNameEmail(userName: String, userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
            preferences[USER_EMAIL] = userEmail
        }
    }
}

data class User(val name: String, val email: String)
