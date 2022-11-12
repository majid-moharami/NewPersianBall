package ir.pattern.persianball.data.model.profile

enum class InfoType(val type: String) {
    NAME("نام"),
    FAMILY_NAME("نام خانوادگی"),
    LATIN_NAME("نام لاتین"),
    LATIN_FAMILY_NAME("نام خانوادگی لاتین"),
    NATION("ملیت"),
    BIRTHDAY("تاریخ تولد"),
    GENDER("جنسیت"),
    NATIONAL_CODE("کد ملی"),
    PHONE_NUMBER("شماره همراه"),
    HOME_PHONE("شماره تلفن ثابت"),
    EMAIL("ایمیل"),
    ADDRESS("آدرس"),
    POSTAL_CODE("کد پستی")
}