package ir.pattern.persianball.utils

import androidx.paging.PagingConfig

fun PagingConfig.Companion.singlePagingConfig(): PagingConfig =
    PagingConfig(pageSize = 0)