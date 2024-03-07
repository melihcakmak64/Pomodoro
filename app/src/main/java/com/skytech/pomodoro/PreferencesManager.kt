package com.skytech.pomodoro
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class PreferencesManager(var context: Context) {
    val Context.ds: DataStore<Preferences> by preferencesDataStore("settings")
    companion object{
        val sound_key= booleanPreferencesKey("sound")

    }

    suspend fun setSound(onOff:Boolean){
        context.ds.edit {
            it[sound_key]=onOff
        }
    }

    suspend fun getSound():Boolean{
        val p=context.ds.data.first()
        return p[sound_key]?:true

    }




}