//package ir.pattern.persianball.views
//
//import android.R
//import android.content.Context
//import android.content.res.ColorStateList
//import android.content.res.Resources.Theme
//import android.util.AttributeSet
//import com.google.android.material.textfield.TextInputLayout
//
//
//class PersianBallInputLayout : TextInputLayout {
//
//    constructor(context: Context) : super(context) {
//        init()
//    }
//
//    constructor(
//        context: Context,
//        attrs: AttributeSet?
//    ) : super(context, attrs) {
//        init()
//    }
//
//    private fun init() {
//        setCornerRadius(30)
//        hintTextColor = resources.getColor(R.color.ripple_color_transparent)
//        setHelperTextColor(UIUtils.getBoxBorderColor())
//        setErrorTextColor(ColorStateList.valueOf(Theme.getCurrent().red))
//        defaultHintTextColor = UIUtils.getBoxBorderColor()
//        placeholderTextColor = UIUtils.getBoxTextColor()
//        counterTextColor = UIUtils.getBoxTextColor()
//        counterOverflowTextColor = UIUtils.getBoxTextColor()
//        setPrefixTextColor(UIUtils.getBoxTextColor())
//        setSuffixTextColor(UIUtils.getBoxTextColor())
//        setBoxStrokeColorStateList(UIUtils.getBoxBorderColor())
//        boxStrokeErrorColor = ColorStateList.valueOf(Theme.getCurrent().red)
//    }
//
//    override fun setErrorEnabled(enabled: Boolean) {
//        if (enabled) {
//            boxStrokeColor = Theme.getCurrent().red
//            hintTextColor = ColorStateList.valueOf(Theme.getCurrent().red)
//        } else {
//            setBoxStrokeColorStateList(UIUtils.getBoxBorderColor())
//            hintTextColor = defaultHintTextColor
//        }
//    }
//
//    fun setCornerRadius(radius: Int) {
//        setBoxCornerRadii(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())
//    }
//    fun getBoxBorderColor(): ColorStateList {
//        val states = arrayOf(intArrayOf(android.R.attr.state_focused),
//            intArrayOf(-android.R.attr.state_enabled), intArrayOf())
//        val colors = intArrayOf(Theme.appPrimary, Theme.getCurrent().disableText,
//            Theme.getCurrent().border)
//        return ColorStateList(states, colors)
//    }
//}