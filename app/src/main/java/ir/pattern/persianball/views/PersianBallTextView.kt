package ir.pattern.persianball.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

class PersianBallTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        Log.d(
            TAG,
            "PersianBallTextView: "
        )
        if (!this.isInEditMode) init(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (!this.isInEditMode) init(context)
    }

    private fun init(context: Context) {
        gravity = gravity
        val typeFace = Typeface.createFromAsset(
            context.assets,
            FONT_FOLDER_IRAN_SANS
        )
        if (typeFace != null) {
            typeface = typeFace
        }
        val text = text.toString()
        setText(text)
    }

    companion object{
        private const val FONT_FOLDER_IRAN_SANS = "font/" + "iran_sans.ttf"
        private const val TAG = "PersianBallTextView"
    }
}