package auto.mod

import android.app.Application
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.util.Log
import auto.mod.plugin.PluginManager
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook

open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        android.util.Log.e("AUTOMOD", "App Test")
        PluginManager.loadPlugins(applicationContext)
        Pine.hook(Activity::class.java.getDeclaredMethod("dispatchTouchEvent", MotionEvent::class.javaObjectType), object : MethodHook() {
            override fun afterCall(callFrame: Pine.CallFrame) {
                val event = callFrame.args[0] as MotionEvent
                val counter = event.pointerCount
                val activity = callFrame.thisObject as Activity

                //Will I change this code immediately? Yes. 
                //But do I have boxes to check? Yes.
                if (event != null) {
                    val action = event.action and MotionEvent.ACTION_MASK
                    when (action) {
                        MotionEvent.ACTION_POINTER_DOWN -> {
                            if (counter == 2) {
                                activity.startActivity(
                                    Intent(
                                        activity,
                                        SettingsActivity::class.java
                                    )
                                )
                            }
                        }
                    }
                }
            }
            override fun beforeCall(callFrame: Pine.CallFrame) {}
        })
    }
}
