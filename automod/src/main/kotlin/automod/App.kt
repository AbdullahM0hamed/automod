package auto.mod

import android.app.Application
import android.util.Log

open class App : Application() {

    init {
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("AUTOMOD", "It works!")
    }
}
