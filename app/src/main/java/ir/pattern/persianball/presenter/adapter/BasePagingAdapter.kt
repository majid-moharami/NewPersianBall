package ir.pattern.persianball.presenter.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.base.RecyclerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BasePagingAdapter : PagingDataAdapter<RecyclerItem, BaseViewHolder<PersianBallRecyclerData>>(DiffUtilCallBack){


    object DiffUtilCallBack : DiffUtil.ItemCallback<RecyclerItem>() {
        override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
            if (oldItem.data is Equatable && newItem.data is Equatable) {
                return oldItem.hashCode() == newItem.hashCode()
            }
            return oldItem.data.javaClass.name == newItem.data.javaClass.name
        }

        override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
            if (oldItem.data is Equatable && newItem.data is Equatable) {
                return oldItem.data == newItem.data
            }
            return false
        }
    }
    lateinit var activity: Context

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>?

    override fun onBindViewHolder(holder: BaseViewHolder<PersianBallRecyclerData>, position: Int) {
        getItem(position)?.let {
            holder.onBindView(it.data)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PersianBallRecyclerData> {
        activity = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder: BaseViewHolder<PersianBallRecyclerData>
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        val view: View = binding?.root ?: inflater.inflate(viewType, parent, false)
        viewHolder = getViewHolder(parent, viewType, view)?.apply {
            setViewDataBinding(binding)
        } as BaseViewHolder<PersianBallRecyclerData>
        return viewHolder
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<PersianBallRecyclerData>) {
        super.onViewAttachedToWindow(holder)
        val adapterPosition = holder.absoluteAdapterPosition
        if (adapterPosition > -1) {
            getItem(adapterPosition)?.let {
                holder.onAttach(it.data)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<PersianBallRecyclerData>) {
        super.onViewDetachedFromWindow(holder)
        val adapterPosition = holder.absoluteAdapterPosition
        if (adapterPosition > -1) {
            getItem(adapterPosition)?.let {
                holder.onDetach(it.data)
            }
        }
    }

    suspend fun submitData(recyclerData: RecyclerData) {
        recyclerData.pagingFlow.collectLatest {
            submitData(it)
        }
    }

    fun submitData(job: Job, persianBallRecyclerData: RecyclerData) {
        CoroutineScope(Dispatchers.Main.immediate + job).launch {
            submitData(persianBallRecyclerData)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.viewType ?: 0
}