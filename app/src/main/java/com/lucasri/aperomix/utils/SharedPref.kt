package com.lucasri.aperomix.utils

import android.content.SharedPreferences
import android.app.Activity
import android.content.Context


object SharedPref {

    private var mSharedPref: SharedPreferences? = null
    var currentUserUid = "currentUserUid"

    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun read(key: String, defValue: String): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String, value: String) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.commit()
    }

    fun read(key: String, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun write(key: String, value: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.commit()
    }

    fun read(key: String, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!)
        prefsEditor.commit()
    }
}