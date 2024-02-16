package auto.mod

import android.app.Activity
import android.os.Bundle
import android.view.View

class SettingsActivity : Activity() {

    override fun onCreate(instance: Bundle?) {
        super.onCreate(instance)
        val view = View(this)
        setContentView(view)
    }
}
