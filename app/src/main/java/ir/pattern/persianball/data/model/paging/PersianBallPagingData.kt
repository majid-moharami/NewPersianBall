package ir.pattern.persianball.data.model.paging

import ir.pattern.persianball.error.ErrorDTO

class PersianBallPagingError(val errorDto: ErrorDTO) : Throwable()