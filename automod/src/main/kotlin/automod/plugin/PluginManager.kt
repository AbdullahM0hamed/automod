package auto.mod.plugin

import android.content.Context
import dalvik.system.PathClassLoader
import java.io.File

object PluginManager {
    public fun loadPlugins(context: Context) {
        context.filesDir
            ?.listFiles { file -> file.isFile && file.name.endsWith(".dex") }
            ?.forEach { dex ->
                getPlugin(context, dex)?.load(context)
            }
    }

    private fun getPlugin(context: Context, dex: File): Plugin? {
        val loader = PathClassLoader(
            dex.absolutePath,
            null,
            context.classLoader
        )
        var plugin: Plugin? = null

        //TODO: Have a manifest for plugins and get this from that
        val pkgName = "auto.mod.ext.ExtPlugin"
        val obj = Class.forName(pkgName, false, loader).newInstance()
        if (obj is Plugin) {
            plugin = obj
        }

        return plugin
    }
}
