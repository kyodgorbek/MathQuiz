package com.yodgorbek.mathquiz

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ScoreDataStore {
    private val Context.dataStore by preferencesDataStore("quiz_score")
    private val HIGH_SCORE_KEY = intPreferencesKey("high_score")

    suspend fun saveHighScore(context: Context, score: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[HIGH_SCORE_KEY] ?: 0
            if (score > current) prefs[HIGH_SCORE_KEY] = score
        }
    }

    fun getHighScore(context: Context): Flow<Int> =
        context.dataStore.data.map { it[HIGH_SCORE_KEY] ?: 0 }
}
