package ir.pattern.persianball.error

import android.content.Context
import java.io.Serializable

class ErrorDTO : Serializable {
    var code: String
    var httpStatus = -1
    var message: String
    var translatedMessage: String
    val extra: String? = null
    //var errorAction: ErrorActionDto? = null

    constructor(errorCode: Int, message: String, translatedMessage: String) {
        this.code = errorCode.toString()
        this.message = message
        this.translatedMessage = translatedMessage
    }

    constructor(code: Int, httpStatus: Int, messageCode: String, translatedMessage: String) {
        this.code = code.toString()
        this.httpStatus = httpStatus
        this.message = messageCode
        this.translatedMessage = translatedMessage
    }

    override fun toString(): String {
        return "ErrorDTO{" +
                "code=" + code +
                ", messageCode='" + message + '\'' +
                '}'
    }

    fun assertToastMessage(context: Context?) {
//		if (!TextUtils.isEmpty(getTranslatedMessage())) {
//			MyketToast.makeText(context, getTranslatedMessage()).setLongTime().show();
//		} else {
//			Assert.fail();
//		}
    }

    companion object {
        const val CODE_AUTHORIZATION_FAILURE = 1
        const val CODE_NO_CONNECTION = 2
        const val CODE_SERVER_SING_OUT = 401

        //	public static final int CODE_NOT_ACCEPTABLE = 406;
        const val CODE_ALREADY_PURCHASED = 510
        const val CODE_CLIENT_ERROR = -1
        const val CODE_CLIENT_GOOGLE_SEARCH_ERROR = -167
        const val CODE_PACKAGE_NAME_NOT_IN_MYKET_ERROR = 200
        const val CODE_FORCE_UPDATE = 426
        const val CODE_ACCESS_DENIED = 400
        const val CODE_LIVE_STREAM_NOT_FOUND = 404
        const val USER_NOT_FOUND = "user_not_found"
        const val INVALID_MOBILE = "invalid_mobile"
        const val MOBILE_EXIST = "mobile_exists"
        const val INVALID_CODE = "invalid_verification_code"
        const val SAME_PASSWORD = "same_password"
        const val WRONG_PASSWORD = "wrong_password"
        const val MESSAGE_CODE_MAX_REACHED = "PinMaxTryReached"
    }
}