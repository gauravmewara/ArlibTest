package com.arlib.arlibtest.common.utils

import android.content.Context
import android.content.SharedPreferences


open class ArlibSharedPreference(context:Context) : IprefHelper {
    private val PREFS_NAME = "ArlibSharedPreference"
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object{
        const val USER_NAME = "user_name"
        const val IS_LOGIN = "is_login"
    }


    override fun setUserName(userName: String) {
        preferences[USER_NAME]=userName
    }

    override fun getUserName(): String {
        return preferences[USER_NAME] ?: ""
    }

    override fun logIn(userName: String) {
        preferences[IS_LOGIN] = true
        preferences[USER_NAME] = userName
    }

    override fun logOut() {
        preferences.deleteKey(USER_NAME)
        preferences.deleteKey(IS_LOGIN)
    }

    override fun deleteKey(key: String) {
        preferences.deleteKey(key)
    }

    override fun isLogin(): Boolean{
        return preferences[IS_LOGIN, false] == true
    }

    override fun clearPrefs() {
        preferences.edit().clear().apply()
    }







    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    private fun SharedPreferences.deleteKey(key:String){
        edit{it.remove(key)}
    }


}