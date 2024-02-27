package auto.mod.plugin

import android.content.Context

abstract class Plugin {
    abstract fun load(context: Context)
}
