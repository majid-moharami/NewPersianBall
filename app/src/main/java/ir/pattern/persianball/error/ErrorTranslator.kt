package ir.pattern.persianball.error

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.pattern.persianball.R
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorTranslator
@Inject
constructor(@ApplicationContext private val context: Context) {

    fun errorConverter(response: Response<*>?, t: Throwable?): ErrorDTO {

        val errorDTO: ErrorDTO
        if (response?.errorBody() != null) {
            errorDTO = try {
                val gson = Gson()
                val data = response.errorBody()?.bytes()?.let { String(it) }
                gson.fromJson(data, ErrorDTO::class.java)
            } catch (e: Exception) {
                ErrorDTO(
                    ErrorDTO.CODE_CLIENT_ERROR,
                    "Error data is not readable",
                    context.resources.getString(R.string.error_dto_default_message)
                )
            }

        } else if (t != null) {
            errorDTO = if (t is IOException) {
                ErrorDTO(
                    ErrorDTO.CODE_NO_CONNECTION, "NoConnectionError",
                    context.resources.getString(R.string.check_connection)
                )
            } else {
                ErrorDTO(
                    ErrorDTO.CODE_NO_CONNECTION, "ConversionIssue",
                    context.resources.getString(R.string.error_dto_default_message)
                )
            }
        } else {
            errorDTO = ErrorDTO(
                ErrorDTO.CODE_CLIENT_ERROR,
                "Unknown error",
                context.resources.getString(R.string.error_dto_default_message)
            )
        }

        return errorDTO
    }
}