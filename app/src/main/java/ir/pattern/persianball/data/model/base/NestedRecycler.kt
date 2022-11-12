package ir.pattern.persianball.data.model.base

import android.os.Parcelable
import android.view.View
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.*
import ir.pattern.persianball.R
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class NestedRecyclerData(val recyclerData: RecyclerData) : PersianBallRecyclerData {

    private val _recyclerState = MutableStateFlow<Parcelable?>(null)
    val recyclerState: StateFlow<Parcelable?> = _recyclerState

    fun setRecyclerState(state: Parcelable) {
        _recyclerState.value = state
    }
}



abstract class NestedRecyclerViewHolder<T>(view: View) :
    BaseViewHolder<T>(view) where T : NestedRecyclerData, T : PersianBallRecyclerData {

    companion object {
        const val DEFAULT_SNAP_HELPER = 0
        const val PLAY_SNAP_HELPER = 1
    }

    var recyclerView: HorizontalRecyclerView = itemView.findViewById(R.id.recycler_view)

    abstract fun getRecyclerAdapter(maxSpan: Int): PagingHorizontalDataAdapter

    abstract fun getSnapHelperType(): Int

//    @Inject
//    lateinit var graphicUtils: GraphicUtils
//
//    @Inject
//    lateinit var languageHelper: LanguageHelper

    private var job: Job = Job()

    private var width: Int = 0
    protected var decor: PagingSpaceItemDecoration? = null
    var pagingAdapter: PagingHorizontalDataAdapter? = null
    lateinit var recyclerAdapter: ConcatAdapter
    var animator = DefaultItemAnimator()

    private val recyclerLayoutManager: GridLayoutManager =
        PersianBallGridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, true).apply {
            isItemPrefetchEnabled = false
        }

    override fun onBindView(data: T) {
       // val tabletOrTwoSided = graphicUtils.isTabletDevice() || !data.isOneSided()
       //
        with(recyclerView) {
            layoutManager = recyclerLayoutManager
            isNestedScrollingEnabled = false
            itemAnimator = animator
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            itemAnimator?.changeDuration = 0
            isNestedScrollingEnabled = false
            //(layoutManager as GridLayoutManager).spanCount = data.getSpanCount()
        }
        setDecoration(data)
       // setupSnapHelper(recyclerView, data)
    }
//
//    protected open fun setupSnapHelper(recyclerView: HorizontalRecyclerView, data: T) {
//        recyclerView.onFlingListener = null
//        val tabletOrTwoSided = graphicUtils.isTabletDevice() || !data.isOneSided()
//        when (getSnapHelperType()) {
//            DEFAULT_SNAP_HELPER -> {
//                val visibleThreshold = if (width > 300) 0.1f else 0.5f
//               /
//            }
//            PLAY_SNAP_HELPER -> {
//                val visibleThresholds = 0.5f
//                val playSnapHelper =
//                    MyketSnapHelper(languageHelper, getVisibleColumn(data).toInt(), getVerticalSpace() +
//                            if (tabletOrTwoSided) 0 else 2 * getVerticalSpace(), !tabletOrTwoSided,
//                        visibleThresholds)
//                playSnapHelper.attachToRecyclerView(recyclerView)
//            }
//            else -> {}
//        }
//    }

    override fun onAttach(data: T) {
        super.onAttach(data)
        job = Job()
        pagingAdapter = getRecyclerAdapter(1).apply {
            addLoadStateListener {
                if (it.append.endOfPaginationReached) {
                    if ((pagingAdapter?.itemCount ?: 0) == 0) {
                        handleAdapterEmpty(data)
                    }
                }
            }
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }.also {
            recyclerView.adapter = it
        }
        pagingAdapter?.width = width
        data.recyclerState.collectLatestLifecycleFlow(job) {
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }
        pagingAdapter?.submitData(job, data.recyclerData)
    }

    protected open fun handleAdapterEmpty(data: T) {
//        if (data is KeyData) {
//            notifyRecycler(RecyclerNotifyData.RemoveByKey(data.key))
//        } else {
//            data.recyclerData.pagingFlow.collectLatestLifecycleFlow(job) {
//                data.nestedListData = data.recyclerData.filter?.let { filter ->
//                    NestedListFilterable.NestedListData(it, filter)
//                }
//                if (data.nestedListData != null) {
//                    notifyRecycler(RecyclerNotifyData.Filter())
//                }
//            }
//        }
    }

//    protected open fun getLoadStateAdapter(adapter: BasePagingAdapter): LoadStateAdapter<*> {
//        return MyketLoadStateNestedAdapter(MultiSelectViewHolder.ViewHolderType.MOVIE, adapter::retry)
//    }

    open fun setDecoration(data: T) {
//        decor?.let {
//            recyclerView.removeItemDecoration(it)
//        }
//        decor = PagingSpaceItemDecoration(0,
//            getVerticalSpace(), 0, 0, data.getSpanCount(), true, true).also {
//            recyclerView.addItemDecoration(it)
//        }
    }

    override fun onDetach(data: T) {
        super.onDetach(data)
        saveRecyclerState(data)
        job.cancel()
        recyclerView.adapter = null
        pagingAdapter = null
    }

    private fun saveRecyclerState(data: T) {
        recyclerView.layoutManager?.onSaveInstanceState()?.also {
            data.setRecyclerState(it)
        }
    }

    protected open fun getVerticalSpace(): Int = 0

    protected open fun getPadding(data: T): Int = 0

    protected open fun getVisibleColumn(data: T): Float = 1f
}

fun <T> Flow<T>.collectLatestLifecycleFlow(job: Job, collect: suspend (T) -> Unit) {
    CoroutineScope(Dispatchers.Main.immediate + job).launch {
        collectLatest(collect)
    }
}