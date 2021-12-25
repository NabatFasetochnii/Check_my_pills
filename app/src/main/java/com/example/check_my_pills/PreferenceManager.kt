package com.example.check_my_pills

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar.getInstance

class PreferenceManager(private val context: Context) {

    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = "User_Pill_Data")

    //Create some keys
    companion object {
        val USER_TEXT = stringPreferencesKey("USER_TEXT")
        val USER_PILL_1 = booleanPreferencesKey("CHECK1")
        val USER_PILL_2 = booleanPreferencesKey("CHECK2")
        val DATE_UPDATE = stringPreferencesKey("DATE_LAST_UPDATE")
    }

    //Store user data
    suspend fun storeUser(user_text: String, check1: Boolean, check2: Boolean) = context.store.edit {
        it[USER_TEXT] = user_text
        it[USER_PILL_1] = check1
        it[USER_PILL_2] = check2
        it[DATE_UPDATE] = getInstance().toString()

    }

    val userDataFlowText: Flow<String> = context.store.data.map {
        it[USER_TEXT] ?: ""
    }

    val userDataFlowPill1: Flow<Boolean> = context.store.data.map {
        it[USER_PILL_1] ?: false
    }

    val userDataFlowPill2: Flow<Boolean> = context.store.data.map {
        it[USER_PILL_2] ?: false
    }

    val userDataFlowLastUpdate: Flow<String> = context.store.data.map {
        it[DATE_UPDATE] ?: ""
    }

}