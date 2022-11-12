package ir.pattern.persianball.data.model.base

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class PersianBallGridLayoutManager: GridLayoutManager {
    var padding: Padding? = null

    constructor(context: Context?, spanCount: Int) : super(context, spanCount)

    constructor(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context,
        spanCount, orientation, reverseLayout)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context,
        attrs, defStyleAttr, defStyleRes)

    override fun requestChildRectangleOnScreen(
        parent: RecyclerView, child: View, rect: Rect, immediate: Boolean): Boolean = false

    override fun requestChildRectangleOnScreen(
        parent: RecyclerView, child: View, rect: Rect, immediate: Boolean,
        focusedChildVisible: Boolean): Boolean = false

    override fun isAutoMeasureEnabled(): Boolean = true

    override fun getPaddingBottom(): Int = padding?.bottom ?: super.getPaddingBottom()
    override fun getPaddingRight(): Int = padding?.right ?: super.getPaddingRight()
    override fun getPaddingLeft(): Int = padding?.left ?: super.getPaddingLeft()
    override fun getPaddingTop(): Int = padding?.top ?: super.getPaddingTop()

    class Padding(val left: Int, val top: Int, val right: Int, val bottom: Int) : Serializable
}