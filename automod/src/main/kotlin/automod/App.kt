package auto.mod

import android.app.Application
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.util.Log
import android.widget.Toast
import auto.mod.plugin.PluginManager
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook

open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        android.util.Log.e("AUTOMOD", "App Test")
        PluginManager.loadPlugins(applicationContext)
        android.widget.Toast.makeText(applicationContext, "Auto mod 1", 5).show()
        android.util.Log.e("AUTOMOD",  "Auto mod 1")
        /*Pine.hook(Activity::class.java.getDeclaredMethod("dispatchTouchEvent", MotionEvent::class.javaObjectType), object : MethodHook() {
            override fun afterCall(callFrame: Pine.CallFrame) {
		val event = callFrame.args[0] as MotionEvent
                val counter = event.pointerCount
                val activity = callFrame.thisObject as Activity
                android.util.Log.e("AUTOMOD", "Counter: " + counter)
                /*if (counter == 2) {
                    activity.startActivity(
                        Intent(
                            activity,
                            SettingsActivity::class.java
                        )
                    )
                }*/
		Log.e("AUTOMOD", event.toString())
                if (event != null) {
                    val action = event.action and MotionEvent.ACTION_MASK
		    Log.e("AUTOMOD", "Action: " + event.action.toString())
                    when (action) {
                        MotionEvent.ACTION_POINTER_DOWN -> {
                            android.util.Log.e("AUTOMOD", "Counter: " + counter)
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
        })*/
        val handler = Handler(Looper.getMainLooper())
        Pine.hook(
            Activity::class.java.getDeclaredMethod(
                "onKeyDown",
                Int::class.javaPrimitiveType,
                KeyEvent::class.javaObjectType
            ),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame) {}
                override fun afterCall(callFrame: Pine.CallFrame) {
                    android.widget.Toast.makeText(applicationContext, "Auto mod 2", 5).show()
                    android.util.Log.e("AUTOMOD",  "Auto mod 2")
                    val keyCode = callFrame.args[0] as Int
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        val activity = callFrame.thisObject as Activity
                        handler.postDelayed({
                            activity.startActivity(
                                Intent(
                                    activity,
                                    SettingsActivity::class.java
                                )
                            )
                        }, 1000)
                    }
                }
            }
        )

        Pine.hook(
            Activity::class.java.getDeclaredMethod(
                "onKeyUp",
                Int::class.javaPrimitiveType,
                KeyEvent::class.javaObjectType
            ),
            object : MethodHook() {
                override fun beforeCall(callFrame: Pine.CallFrame) {}
                override fun afterCall(callFrame: Pine.CallFrame) {
                    android.widget.Toast.makeText(applicationContext, "Auto mod 3", 5).show()
                    android.util.Log.e("AUTOMOD",  "Auto mod 3")
                    val keyCode = callFrame.args[0] as Int
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        handler.removeCallbacksAndMessages(null)
                    }
                }
            }
        )
    }
}
