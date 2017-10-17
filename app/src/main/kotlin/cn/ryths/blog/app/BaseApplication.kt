package cn.ryths.blog.app

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        _context = applicationContext
    }

    companion object {
        private var _context: Context? = null
        fun getContext(): Context? {
            return _context
        }
    }
}