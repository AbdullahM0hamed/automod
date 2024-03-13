package auto.mod

import android.app.Activity
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

class SettingsActivity : Activity() {
    override fun onCreate(instance: Bundle?) {
        super.onCreate(instance)

	try {
            val parser = assets.openXmlResourceParser("assets/automod.xml")
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(parser, LinearLayout(this))
            setContentView(view)

            val infoTextView = view.findViewById(2131165186) as TextView
	    infoTextView.text = "Add additional repos to automod. This should be a URL that ends with \"updater.json\""
            val color = infoTextView.currentTextColor
            val bg = color xor 0x00ffffff.toInt()
            view.setBackgroundColor(bg)

            //TODO: make it have the right drawable when beinf typed in only 
            /*val editable = view.findViewById(2131165188) as TextView
            editable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
	    val add = getAssetDrawable("done.xml")
            editable.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                add,
                null
            )*/

            val helpIconView = view.findViewById(2131165185) as ImageView
            helpIconView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val helpIcon = getAssetDrawable("help.xml")
            helpIconView.setImageDrawable(helpIcon)

            val addIconView = view.findViewById(2131165187) as ImageView
            addIconView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val addIcon = getAssetDrawable("add.xml")
            addIconView.setImageDrawable(addIcon)
        } catch (e: IOException) {} catch (e: XmlPullParserException) {}
    }

    private fun getAssetDrawable(path: String): Drawable? {
        var drawable: Drawable? = null
        try {
            val parser = assets.openXmlResourceParser("assets/" + path)
            drawable = Drawable.createFromXml(resources, parser)
        } catch (ex: IOException) {
        } catch (ex: XmlPullParserException) {}

        return drawable
    }
}
