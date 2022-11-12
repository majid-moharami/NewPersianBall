package ir.pattern.persianball.data.model.base

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import com.google.firebase.firestore.util.Assert
import java.io.Serializable


class ErrorDTO : Serializable {
	var code: Int
	var httpStatus = -1
	var messageCode: String
	var translatedMessage: String
	val extra: String? = null
//	var errorAction: ErrorActionDto? = null

	constructor(errorCode: Int, message: String, translatedMessage: String) {
		this.code = errorCode
		messageCode = message
		this.translatedMessage = translatedMessage
	}

	constructor(code: Int, httpStatus: Int, messageCode: String, translatedMessage: String) {
		this.code = code
		this.httpStatus = httpStatus
		this.messageCode = messageCode
		this.translatedMessage = translatedMessage
	}

	override fun toString(): String {
		return "ErrorDTO{" +
				"code=" + code +
				", messageCode='" + messageCode + '\'' +
				'}'
	}

	@SuppressLint("RestrictedApi")
	fun assertToastMessage(context: Context?) {
		if (!TextUtils.isEmpty(translatedMessage)) {
			//MyketToast.makeText(context, translatedMessage).setLongTime().show()
		} else {
			Assert.fail("")
		}
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
		const val MESSAGE_CODE_SIGN_NOT_MATCHED = "SignNotMatched"
	}
}
