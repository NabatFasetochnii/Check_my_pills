package com.muniaccidente.check_my_pills

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager(private val context: Context) {

    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = "User_Pill_Data")

    //Create some keys
    companion object {
        val USER_TEXT1 = stringPreferencesKey("USER_TEXT1")
        val USER_TEXT2 = stringPreferencesKey("USER_TEXT2")
        val USER_TEXT3 = stringPreferencesKey("USER_TEXT3")
        val USER_TEXT4 = stringPreferencesKey("USER_TEXT4")
        val USER_PILL_1 = intPreferencesKey("CHECK1")
        val USER_PILL_2 = intPreferencesKey("CHECK2")
        val USER_PILL_3 = intPreferencesKey("CHECK3")
        val USER_PILL_4 = intPreferencesKey("CHECK4")
        val DATE_UPDATE = stringPreferencesKey("DATE_LAST_UPDATE")
    }

    //Store user data
    suspend fun storeUser(user_text1: String, user_text2: String, user_text3: String, user_text4: String,
                          check1: Int, check2: Int, check3: Int, check4: Int) = context.store.edit {
        it[USER_TEXT1] = user_text1
        it[USER_TEXT2] = user_text2
        it[USER_TEXT3] = user_text3
        it[USER_TEXT4] = user_text4
        it[USER_PILL_1] = check1
        it[USER_PILL_2] = check2
        it[USER_PILL_3] = check3
        it[USER_PILL_4] = check4
        it[DATE_UPDATE] = java.time.LocalDate.now().toString()
    }

    val userDataFlowText1: Flow<String> = context.store.data
        .map {
            it[USER_TEXT1] ?: ""
        }

    val userDataFlowText2: Flow<String> = context.store.data
        .map {
            it[USER_TEXT2] ?: ""
        }

    val userDataFlowText3: Flow<String> = context.store.data
        .map {
            it[USER_TEXT3] ?: ""
        }

    val userDataFlowText4: Flow<String> = context.store.data
        .map {
            it[USER_TEXT4] ?: ""
        }

    val userDataFlowPill1: Flow<Int> = context.store.data
        .map {
            it[USER_PILL_1] ?: 0
        }

    val userDataFlowPill2: Flow<Int> = context.store.data
        .map {
            it[USER_PILL_2] ?: 0
        }

    val userDataFlowPill3: Flow<Int> = context.store.data
        .map {
            it[USER_PILL_3] ?: 0
        }

    val userDataFlowPill4: Flow<Int> = context.store.data
        .map {
            it[USER_PILL_4] ?: 0
        }


    val userDataFlowLastUpdate: Flow<String> = context.store.data
        .map {
            it[DATE_UPDATE] ?: ""
        }

}