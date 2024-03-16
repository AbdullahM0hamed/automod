package auto.mod

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
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

            val add = getAssetDrawable("done.xml")
            val editable = view.findViewById(2131165189) as TextView
            editable.setOnFocusChangeListener { _, hasFocus ->
                val repoRow = editable.parent as LinearLayout
                if (hasFocus) {
                    editable.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        add,
                        null
                    )
                    repoRow.setBackgroundColor(editable.highlightColor)
                } else {
                    editable.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                    repoRow.setBackgroundColor(android.R.color.transparent)
                }
            }
            editable.setOnTouchListener { edittext, event ->
                if (event.action == MotionEvent.ACTION_UP && event.rawX >= edittext.right - (edittext as TextView).totalPaddingRight) {
                    edittext.clearFocus()
                    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromWindow(edittext.windowToken, 0)
                    return@setOnTouchListener true
                }

                return@setOnTouchListener false
            }
            add?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val helpIconView = view.findViewById(2131165185) as ImageView
            helpIconView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val helpIcon = getAssetDrawable("help.xml")
            helpIconView.setImageDrawable(helpIcon)

            val addIconView = view.findViewById(2131165188) as ImageView
            addIconView.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val addIcon = getAssetDrawable("add.xml")
            addIconView.setImageDrawable(addIcon)

            val list = view.findViewById(2131165190) as ListView
            val adapter = RepoAdapter(
                this,
                "assets/repository_item.xml",
                2131165194,
                2131165195,
                arrayOf(Repo("test", "test"))
            )
            list.adapter = adapter
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

    data class Repo(
        val name: String,
        val link: String
    )

    class RepoAdapter(
        val ctx: Context,
        val layout: String,
        val mainText: Int,
        val subText: Int,
        val repos: Array<Repo>
    ) : ArrayAdapter<String>(ctx, 0) {
        override fun getView(pos: Int, view: View?, parent: ViewGroup): View {
            val item = getLayout(parent)
            item?.apply {
                val main = findViewById(mainText) as TextView
                main.text = repos[pos].name

                val sub = findViewById(subText) as TextView
                sub.text = repos[pos].link
            }

            return item ?: View(ctx)
        }

        override fun getCount(): Int = repos.size

        private fun getLayout(parent: ViewGroup): View? {
            var item: View? = null

            try {
                val parser = ctx.assets.openXmlResourceParser(layout)
                item = LayoutInflater.from(ctx).inflate(parser, parent, false)
            } catch (e: IOException) {}

            return item
        }
    }
}
