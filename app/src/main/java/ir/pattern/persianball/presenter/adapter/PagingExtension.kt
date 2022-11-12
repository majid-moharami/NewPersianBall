package ir.pattern.persianball.presenter.adapter

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.flatMap
import ir.pattern.persianball.data.model.RecyclerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T : Any> Flow<PagingData<T>>.mapToRecyclerItem(
    mapper: (T) -> List<RecyclerItem>): Flow<PagingData<RecyclerItem>> = this.map {
    it.flatMap { data ->
        mapper(data)
    }
}