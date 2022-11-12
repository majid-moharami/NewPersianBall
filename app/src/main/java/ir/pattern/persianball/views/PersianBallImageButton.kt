package ir.pattern.persianball.views

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersianBallImageButton constructor(context: Context, attr: AttributeSet) :
    AppCompatImageButton(context, attr) {
    private var isDisabled = false

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setDisable(isDisable: Boolean, disableDrawable: Drawable?) {
        isDisabled = isDisable
        isFocusable = isDisable
        isFocusableInTouchMode = isDisable
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            background = disableDrawable
        } else {
            setBackgroundDrawable(disableDrawable)
        }
    }

    fun isDisabled(): Boolean {
        return isDisabled
    }

}