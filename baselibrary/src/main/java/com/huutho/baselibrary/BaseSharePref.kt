package com.huutho.baselibrary

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

open class BaseSharePref(
    private val context: Context,
    private val nameOfSharePref: String,
    private val gson: Gson
) {
    private val sharePref: SharedPreferences by lazy {
        context.getSharedPreferences(nameOfSharePref, Context.MODE_PRIVATE)
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        return when (anonymousClass) {
            String::class.java -> sharePref.getString(key, "") as T
            Boolean::class.java -> java.lang.Boolean.valueOf(sharePref.getBoolean(key, false)) as T
            Float::class.java -> java.lang.Float.valueOf(sharePref.getFloat(key, -1f)) as T
            Int::class.java -> Integer.valueOf(sharePref.getInt(key, -1)) as T
            Long::class.java -> java.lang.Long.valueOf(sharePref.getLong(key, -1)) as T
            else -> gson.fromJson(sharePref.getString(key, ""), anonymousClass)
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = sharePref.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, gson.toJson(data))
        }
        editor.apply()
    }

    fun remove(key: String) {
        val editor = sharePref.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        sharePref.edit().clear().apply()
    }
}