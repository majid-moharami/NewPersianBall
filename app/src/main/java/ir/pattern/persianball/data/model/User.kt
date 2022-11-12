package ir.pattern.persianball.data.model

data class User(
    var userName: String?,
    var password: String?,
    var token: String?,
    var refreshToken: String?
)
