package ir.pattern.persianball.data.model

data class UserCredential(
    val username: String,
    var password: String,
    var token: String,
    var refreshToken: String,
    var profileImageUrl: String
)
