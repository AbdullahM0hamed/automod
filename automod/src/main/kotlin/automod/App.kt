package auto.mod

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook

open class App : Application() {

    init {
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("AUTOMOD", "It works!")
        Pine.hook(Activity::class.java.getDeclaredMethod("onCreate", Bundle::class.javaObjectType), object : MethodHook() {
            override fun beforeCall(callFrame: Pine.CallFrame) {
                Log.e("AUTOMOD", "Before " + callFrame.thisObject + " onCreate()")
            }

            override fun afterCall(callFrame: Pine.CallFrame) {
                Log.e("AUTOMOD", "After " + callFrame.thisObject + " onCreate()")
            }
        })
    }
}
