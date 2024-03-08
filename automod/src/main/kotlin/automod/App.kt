package auto.mod

import android.app.Application
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ActionMode
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import auto.mod.plugin.PluginManager
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook

open class App : Application(), Application.ActivityLifecycleCallbacks,  Window.Callback {
    var handler: Handler? = null
    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
        PluginManager.loadPlugins(applicationContext)
        registerActivityLifecycleCallbacks(this)
        addDynamicShortcut(this, "automod", "Automod Settings")
	/*Activity::class.java.getDeclaredMethods().forEach { method ->
             Pine.hook(method, object : MethodHook() {
                 override fun afterCall(callFrame: Pine.CallFrame) {
                     android.util.Log.e("AUTOMOD", "Hooked: " + method.name)
                 }
            })
        }*/
    }

    private fun getBitmapFromAssets(context: Context, fileName: String): Bitmap {
        val inputStream = context.assets.open(fileName)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun addDynamicShortcut(context: Context, shortcut: String, label: String) {
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        val targetIntent = Intent(context, SettingsActivity::class.java)
        targetIntent.action = Intent.ACTION_VIEW
        val icon = Icon.createWithBitmap(getBitmapFromAssets(context, "automod.png"))
        val shortcut = ShortcutInfo.Builder(context, shortcut)
            .setShortLabel(label)
            .setIcon(icon)
            .setIntent(targetIntent)
            .build()
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager.dynamicShortcuts = listOf(shortcut)
        }
    }

    var activity: Activity? = null
    override fun onActivityCreated(activity: Activity, state: Bundle?) {
        if (activity::class.java.name == "auto.mod.SettingsActivity") {
            return
        }
	activity.window.callback = this
	this.activity = activity
    }
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, state: Bundle) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun dispatchGenericMotionEvent(e: MotionEvent) = activity?.dispatchGenericMotionEvent(e) ?: true
    private fun keyHandler(event: KeyEvent) {
        android.util.Log.e("AUTOMOD", "Log")
        /*if (activity != null) {
            android.util.Log.e("AUTOMOD", activity!!::class.java.name)
            if (activity!!::class.java.name == "auto.mod.SettingsActivity") {
                return activity?.dispatchKeyEvent(event) ?: true
            }
        }*/

        when (event.action) {
            KeyEvent.ACTION_DOWN -> {
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                    android.util.Log.e("AUTOMOD", "onDown")
                    handler?.postDelayed({
                        val intent = Intent(this, SettingsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                    }, ViewConfiguration.getLongPressTimeout().toLong())
                }
            }
            KeyEvent.ACTION_UP -> {
                if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                    android.util.Log.e("AUTOMOD", "onUp")
                    handler?.removeCallbacksAndMessages(null)
                }
            }
        }
    }
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        android.util.Log.e("AUTOMOD", "dispatchKeyEvent")
        android.util.Log.e("AUTOMOD", "Event: " + event.toString())
        android.util.Log.e("AUTOMOD", "Action: " + event.action.toString())
        android.util.Log.e("AUTOMOD", "Code: " + event.keyCode.toString())
        keyHandler(event)
	return activity?.dispatchKeyEvent(event) ?: true
    }
    override fun dispatchKeyShortcutEvent(event: KeyEvent): Boolean {
        android.util.Log.e("AUTOMOD", "dispatchKeyShortcutEvent")
        android.util.Log.e("AUTOMOD", "Event: " + event.toString())
        android.util.Log.e("AUTOMOD", "Action: " + event.action.toString())
        android.util.Log.e("AUTOMOD", "Code: " + event.keyCode.toString())
        keyHandler(event)
        return activity?.dispatchKeyShortcutEvent(event) ?: true
    }
    override fun dispatchPopulateAccessibilityEvent(e: AccessibilityEvent) = activity?.dispatchPopulateAccessibilityEvent(e) ?: true
    override fun dispatchTouchEvent(e: MotionEvent) = activity?.dispatchTouchEvent(e) ?: true
    override fun dispatchTrackballEvent(e: MotionEvent) = activity?.dispatchTrackballEvent(e) ?: true
    override fun onActionModeFinished(m: ActionMode) {
        activity?.onActionModeFinished(m)
    }
    override fun onActionModeStarted(m: ActionMode) {
        activity?.onActionModeStarted(m)
    }
    override fun onAttachedToWindow() { activity?.onAttachedToWindow() }
    override fun onContentChanged() { activity?.onContentChanged() }
    override fun onCreatePanelMenu(i: Int, m: Menu) = activity?.onCreatePanelMenu(i, m) ?: true
    override fun onCreatePanelView(f: Int): View? = activity?.onCreatePanelView(f)
    override fun onDetachedFromWindow() { activity?.onDetachedFromWindow() }
    override fun onMenuItemSelected(i: Int, mi: MenuItem) = activity?.onMenuItemSelected(i, mi) ?: true
    override fun onMenuOpened(i: Int, m: Menu) = activity?.onMenuOpened(i, m) ?: true
    override fun onPanelClosed(i: Int, m: Menu) { activity?.onPanelClosed(i, m) }
    override fun onPreparePanel(i: Int, v: View?, m: Menu) = activity?.onPreparePanel(i, v, m) ?: true
    override fun onSearchRequested() = activity?.onSearchRequested() ?: true
    override fun onSearchRequested(e: SearchEvent) = activity?.onSearchRequested(e) ?: true
    override fun onWindowAttributesChanged(a: WindowManager.LayoutParams) { activity?.onWindowAttributesChanged(a) }
    override fun onWindowFocusChanged(f: Boolean) { activity?.onWindowFocusChanged(f) }
    override fun onWindowStartingActionMode(c: ActionMode.Callback) = activity?.onWindowStartingActionMode(c)
    override fun onWindowStartingActionMode(c: ActionMode.Callback, t: Int) = activity?.onWindowStartingActionMode(c, t)
}
