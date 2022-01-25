package com.arlib.arlibtest.common.utils

interface IprefHelper {
    fun setUserName(userName: String)
    fun getUserName(): String
    fun logIn(userName:String)
    fun logOut()
    fun deleteKey(key:String)
    fun isLogin():Boolean
    fun clearPrefs()
}