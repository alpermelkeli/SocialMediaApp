package com.alpermelkeli.socialmediaapp.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeData")
        val USERNAME = stringPreferencesKey("store_username")
        val PROFILE_PHOTO = stringPreferencesKey("store_profile_photo")
        val E_MAIL = stringPreferencesKey("store_email")
    }

    val getUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME] ?: ""
        }

    val getProfilePhoto: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_PHOTO] ?: ""
        }

    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[E_MAIL] ?: ""
        }

    suspend fun saveUsername(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = name
        }
    }

    suspend fun saveProfilePhoto(photoUrl: String) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_PHOTO] = photoUrl
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[E_MAIL] = email
        }
    }
}
