package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.ErrorDTO
import retrofit2.Response
import java.lang.Error

class Request {
    companion object{
        suspend fun <T> getResponse(request: suspend () -> Response<T>): Resource<T> {
            return try {
                val result = request.invoke()
                if (result.isSuccessful) {
                    Resource.Success(result.body()) as Resource<T>
                } else {
                    result.message()
                    Resource.Failure(ErrorDTO(result.code(), result.message(), ""))
                }
            } catch (e: Throwable) {
                val s = e
                Resource.Failure(ErrorDTO(1, e.message.toString(), ""))
            }
        }
    }
}