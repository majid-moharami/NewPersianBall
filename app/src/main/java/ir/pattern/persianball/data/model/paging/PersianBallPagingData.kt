package ir.pattern.persianball.data.model.paging

import ir.pattern.persianball.data.model.base.ErrorDTO

class PersianBallPagingError(val errorDto: ErrorDTO) : Throwable()