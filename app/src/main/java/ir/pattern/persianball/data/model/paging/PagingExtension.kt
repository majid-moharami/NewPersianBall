package ir.pattern.persianball.data.model.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.flatMap
import ir.pattern.persianball.data.model.RecyclerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//
//fun PagingConfig.Companion.singlePagingConfig(): PagingConfig =
//    PagingConfig(pageSize = 0)
//
//fun PagingConfig.Companion.defaultMyketPagingConfig(): PagingConfig =
//    PagingConfig(pageSize = ApplicationLauncher.getContext().resources.getInteger(
//        R.integer.collection_limit),
//        prefetchDistance = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_prefetch_size),
//        initialLoadSize = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_limit))
//
//fun PagingConfig.Companion.defaultMoviePagingConfig(): PagingConfig =
//    PagingConfig(pageSize = ApplicationLauncher.getContext().resources.getInteger(
//        R.integer.collection_limit_movie),
//        prefetchDistance = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_prefetch_size),
//        initialLoadSize = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_limit_movie))
//
//fun PagingConfig.Companion.defaultMovieNestedPagingConfig(): PagingConfig =
//    PagingConfig(
//        initialLoadSize = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_limit_movie),
//        pageSize = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.collection_limit_movie),
//        prefetchDistance = ApplicationLauncher.getContext().resources.getInteger(
//            R.integer.nested_collection_prefetch_size))

fun <T : Any> Flow<PagingData<T>>.mapToRecyclerItem(
    mapper: (T) -> List<RecyclerItem>): Flow<PagingData<RecyclerItem>> = this.map {
    it.flatMap { data ->
        mapper(data)
    }
}