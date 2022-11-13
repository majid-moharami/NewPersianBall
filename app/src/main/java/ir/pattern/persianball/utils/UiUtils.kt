package ir.pattern.persianball.utils

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue

object UiUtils {
    val englishToPersian: MutableMap<Char, Char>

    init {
        englishToPersian = HashMap(10)
        englishToPersian['0'] = '۰'
        englishToPersian['1'] = '۱'
        englishToPersian['2'] = '۲'
        englishToPersian['3'] = '۳'
        englishToPersian['4'] = '۴'
        englishToPersian['5'] = '۵'
        englishToPersian['6'] = '۶'
        englishToPersian['7'] = '۷'
        englishToPersian['8'] = '۸'
        englishToPersian['9'] = '۹'
    }

    fun convertToPersianNumber(str: String): String {
        if (TextUtils.isEmpty(str) || str.equals("null", ignoreCase = true)) {
            return str
        }
        val charArray = str.toCharArray()
        for (i in charArray.indices) {
            if (englishToPersian.containsKey(charArray[i])) {
                charArray[i] = englishToPersian[charArray[i]]!!
            }
        }
        return String(charArray)
    }

    fun convertGenderToPersianString(gender: String): String {
        when (gender) {
            "male" -> {
                return "مرد"
            }
            "female" -> {
                return "زن"
            }
        }
        return ""
    }

    fun convertGenderToEnglishString(gender: String): String {
        when (gender) {
            "مرد" -> {
                return "male"
            }
            "زن" -> {
                return "female"
            }
        }
        return ""
    }

    fun convertYear(str: Int): String {
        return "$str".substring(("$str".length - 2).coerceAtLeast(0));
    }

    fun convertMonth(month: Int): String {
        if (month < 10) {
            return "0$month"
        }
        return "$month"
    }

    fun convertDay(day: Int): String {
        if (day < 10) {
            return "0$day"
        }
        return "$day"
    }

    fun convertTime(time: Int): String {
        if (time < 10) {
            return "0$time"
        } else {
            return "$time"
        }
    }

    fun dp2px(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics
        ).toInt()
    }
}