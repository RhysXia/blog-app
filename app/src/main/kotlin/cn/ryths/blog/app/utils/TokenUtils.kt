package cn.ryths.blog.app.utils

import android.content.Context

object TokenUtils {
    fun saveToken(context: Context, token: String) {
        val store = context.getSharedPreferences("system", Context.MODE_PRIVATE)
        store.edit()
                .putString("token", token)
                .apply()
    }

    fun getToken(context: Context): String? {
        val store = context.getSharedPreferences("system", Context.MODE_PRIVATE)
        return store.getString("token", null)
    }

    fun removeToken(context: Context) {
        val store = context.getSharedPreferences("system", Context.MODE_PRIVATE)
        store.edit()
                .remove("token")
                .apply()
    }
}