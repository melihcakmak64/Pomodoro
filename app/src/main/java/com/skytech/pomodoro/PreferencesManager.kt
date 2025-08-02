package com.skytech.pomodoro

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class PreferencesManager private constructor(private val context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesManager(context.applicationContext).also { INSTANCE = it }
            }
        }

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val SOUND_KEY = booleanPreferencesKey("sound")
    }

    private val dataStore = context.dataStore

    suspend fun setSound(onOff: Boolean) {
        dataStore.edit {
            it[SOUND_KEY] = onOff
        }
    }

    suspend fun getSound(): Boolean {
        val prefs = dataStore.data.first()
        return prefs[SOUND_KEY] ?: true
    }
}
