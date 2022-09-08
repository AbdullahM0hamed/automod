package automod

import android.app.Application
import android.util.Log

open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("AUTOMOD", "It works!")
    }
}
