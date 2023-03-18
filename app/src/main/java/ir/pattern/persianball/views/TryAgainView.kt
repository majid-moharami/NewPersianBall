package ir.pattern.persianball.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.*
import ir.pattern.persianball.R
import javax.inject.Inject


class TryAgainView : FrameLayout, View.OnClickListener {
    lateinit var root: View
    lateinit var loadingProgressBar: ProgressBar
    lateinit var tryAgainButton: Button
    lateinit var tryAgainMessage: TextView
    lateinit var tryAgainLayout: LinearLayout
    private var onTryAgainListener: OnTryAgainListener? = null
    private var isTrying = false

//    private fun init() {
//        root = inflate(context, R.layout.fragment_try_again, this)
//
//        tryAgainButton = root.findViewById<View>(R.id.btnTryAgain) as Button
//        tryAgainMessage = root.findViewById<View>(R.id.txtTryAgain) as TextView
//        tryAgainLayout = root.findViewById<View>(R.id.layoutTryAgain) as LinearLayout
//        tryAgainButton.setOnClickListener(this)
//    }

    override fun onClick(v: View) {
        startTry()
        if (onTryAgainListener != null) {
            onTryAgainListener?.onTryAgain()
        }
    }

    interface OnTryAgainListener {
        fun onTryAgain()
    }

    @Inject
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init()
    }

    private fun init() {
        root = inflate(context, R.layout.try_again_view, this)
        tryAgainButton = root.findViewById<View>(R.id.btnTryAgain) as Button
        tryAgainMessage = root.findViewById<View>(R.id.txtTryAgain) as TextView
        tryAgainLayout = root.findViewById<View>(R.id.layoutTryAgain) as LinearLayout
        tryAgainButton.setOnClickListener(this)
    }

    fun setOnTryAgainListener(onTryAgainListener: OnTryAgainListener?) {
        this.onTryAgainListener = onTryAgainListener
    }

    fun executeTry() {
        if (onTryAgainListener != null) {
            onTryAgainListener?.onTryAgain()
        }
    }

    fun startTry() {
        isTrying = true
        tryAgainLayout.visibility = GONE
        loadingProgressBar.visibility = VISIBLE
        visibility = VISIBLE
    }

    fun tryFailed(message: String?) {
        isTrying = false
        tryAgainLayout.visibility = VISIBLE
        loadingProgressBar.visibility = GONE
        tryAgainMessage.text = message
        visibility = VISIBLE
    }

    fun tryFinished() {
        isTrying = false
        tryAgainLayout.visibility = GONE
        loadingProgressBar.visibility = GONE
        visibility = GONE
    }

    fun isTrying(): Boolean {
        return isTrying || visibility == GONE
    }
}