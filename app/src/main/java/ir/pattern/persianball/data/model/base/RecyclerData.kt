package ir.pattern.persianball.data.model.base

import androidx.paging.PagingData
import ir.pattern.persianball.data.model.RecyclerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class RecyclerData(var pagingFlow: Flow<PagingData<RecyclerItem>>) {
    constructor(list: List<RecyclerItem>) : this(flowOf(PagingData.from(list)))
    constructor() : this(flowOf(PagingData.empty()))
}