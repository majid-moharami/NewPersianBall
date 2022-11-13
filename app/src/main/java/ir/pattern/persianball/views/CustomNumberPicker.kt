package ir.pattern.persianball.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import ir.pattern.persianball.R


class CustomNumberPicker(context: Context, attrs: AttributeSet) :
		NumberPicker(context, attrs) {
	override fun addView(child: View) {
		super.addView(child)
		updateView(child)
	}

	override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
		super.addView(child, index, params)
		updateView(child)
	}

	override fun addView(child: View, params: ViewGroup.LayoutParams?) {
		super.addView(child, params)
		updateView(child)
	}

	private fun updateView(view: View) {
		val typeface = ResourcesCompat.getFont(context, R.font.iran_sans)
		if (view is EditText) {
			(view as EditText).textSize = 25f
			(view as EditText).typeface = typeface
		}
	}
}