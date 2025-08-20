package uz.gita.a2048

import android.content.SharedPreferences
import android.util.Log

object LocalStorage {
    private lateinit var sharedPreferences: SharedPreferences
    private const val DATA: String = "save_data_game"
    private const val RECORD_DATA = "record"
    private const val SCORE_DATA = "score"
    private const val GAME_STATE = "new_or_continue"
    private const val MUSIC_STATE = "music_on_or_off"

    fun setSharedPref(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    fun getShared() = sharedPreferences

    fun saveNumbers(matrix: Array<Array<Int>>) {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                getShared().edit()?.putInt(DATA + "${4 * i + j}", matrix[i][j])?.apply()
            }
        }
    }

    fun getNumbers(): Array<Array<Int>> {
        val matrix: Array<Array<Int>> = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                matrix[i][j] = getShared().getInt(DATA + "${4 * i + j}", 0)
                Log.d("ZZZ", "getNumbers: ${matrix[i][j]}")
            }
        }
        return matrix
    }

    fun setGameState(bool: Boolean) {
        getShared().edit().putBoolean(GAME_STATE, bool).apply()
    }

    fun getGAmeState(): Boolean {
        return getShared().getBoolean(GAME_STATE, false)
    }

    fun saveMusicState(bool: Boolean){
        getShared().edit().putBoolean(MUSIC_STATE, bool).apply()
    }

    fun getMusicState(): Boolean{
        return getShared().getBoolean(MUSIC_STATE, false)
    }

    fun saveScore(score: Int) {
        getShared().edit().putInt(SCORE_DATA, score).apply()
    }

    fun getScore(): Int {
        Log.d("score", "getScore: ${SCORE_DATA}")
        return getShared().getInt(SCORE_DATA, 0)
    }

    fun saveRecord(record: Int) {
        getShared().edit().putInt(RECORD_DATA, record).apply()
    }

    fun getRecord(): Int {
        return getShared().getInt(RECORD_DATA, 0)
    }
}