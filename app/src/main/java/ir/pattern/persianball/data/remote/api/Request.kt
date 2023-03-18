package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.error.ErrorTranslator
import retrofit2.Response

class Request {
    companion object{
        suspend fun <T> getResponse(request: suspend () -> Response<T>, errorTranslator: ErrorTranslator): Resource<T> {
            return try {
                val result = request.invoke()
                if (result.isSuccessful) {
                    Resource.Success(result.body()) as Resource<T>
                } else {
                    val errorDto = errorTranslator.errorConverter(result, null)
                    result.message()
                    Resource.Failure(errorDto, result.body())
                }
            } catch (e: Throwable) {
                val errorDto = errorTranslator.errorConverter(null, e)
                Resource.Failure(errorDto, null)
            }
        }
    }
}