package ir.pattern.persianball.data.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.pattern.persianball.data.model.Resource

class PagingSourceSinglePage<T : Any>(private val networkCall: suspend () -> Resource<T>) :
    PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val response = networkCall.invoke()
            if (response is Resource.Success) {
                LoadResult.Page(data = listOf(response.data), prevKey = null, nextKey = null)
            } else {
                LoadResult.Error(PersianBallPagingError((response as Resource.Failure).error))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}