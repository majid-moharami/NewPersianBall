package ir.pattern.persianball.utils

import androidx.paging.filter
import androidx.paging.map
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.base.RecyclerData
import kotlinx.coroutines.flow.map

sealed class PagingCrudFunctions {
    class Edit(val predict: (RecyclerItem) -> Boolean,
               val editData: (index: Int, RecyclerItem) -> RecyclerItem) : PagingCrudFunctions()


    class Remove(val predict: ((RecyclerItem) -> Boolean)) : PagingCrudFunctions()
}

fun RecyclerData.edit(predict: (RecyclerItem) -> Boolean,
                      editData: (index: Int, RecyclerItem) -> RecyclerItem): RecyclerData {
    return RecyclerData(pagingFlow.map { pagingData ->
        var index = 0
        pagingData.map {
            if (predict.invoke(it)) {
                editData.invoke(index, it)
            } else {
                it
            }.also {
                index ++
            }
        }
    })
}

fun RecyclerData.remove(predict: ((RecyclerItem) -> Boolean)): RecyclerData =
    RecyclerData(pagingFlow.map { pagingData ->
        pagingData.filter {
            !predict.invoke(it)
        }
    })