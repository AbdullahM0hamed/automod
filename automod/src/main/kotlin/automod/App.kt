package auto.mod

import android.app.Application
import auto.mod.plugin.PluginManager

open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        android.util.Log.e("AUTOMOD", "App Test")
        PluginManager.loadPlugins(applicationContext)
    }
}
