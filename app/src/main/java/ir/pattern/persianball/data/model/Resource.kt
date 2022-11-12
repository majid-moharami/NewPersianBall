package ir.pattern.persianball.data.model

import ir.pattern.persianball.data.model.base.ErrorDTO

sealed class Resource<out R> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Failure<T>(val error: ErrorDTO) : Resource<T>()
}