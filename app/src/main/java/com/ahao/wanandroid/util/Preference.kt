package com.ahao.wanandroid.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.ahao.wanandroid.App
import com.ahao.wanandroid.LaunchingActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.reflect.KProperty

class Preference<T>(val name: String, private val default: T, private var commit: Boolean = false) {
    companion object {
        private const val FILE_NAME = "com_ahao_file"
        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        }

        fun clearPreference(key: String? = null) {
            if (key == null || key == "") {
                prefs.edit().clear().apply()
            } else {
                prefs.edit().remove(key).apply()
            }
        }

        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        with(prefs) {
            val ret: Any = when (default) {
                is Long -> getLong(name, default)
                is String -> getString(name, default)
                is Int -> getInt(name, default)
                is Boolean -> getBoolean(name, default)
                is Float -> getFloat(name, default)

                else -> {
                    val string = getString(name, "")
                    if (TextUtils.isEmpty(string)) {
                        return default
                    }
                    return deSerialization(string)
                }
            }
            return ret as T
        }
    }

    @SuppressLint("CommitPrefEdits")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        prefs.edit().apply{
            when (value) {
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                else -> putString(name, serialization(value))
            }
            if(commit){
                commit()
            }else{
                apply()
            }
        }
    }

    private fun deSerialization(string: String?): T {
        val ois = ObjectInputStream(ByteArrayInputStream(string?.toByteArray(Charsets.UTF_8)))
        val ret = ois.readObject() as T
        ois.close()
        return ret
    }

    private fun <A> serialization(obj: A): String {
        val baos = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(baos)
        objectOutputStream.writeObject(obj)
        val toString = baos.toString("UTF-8")
        objectOutputStream.close()
        baos.close()
        return toString
    }

}