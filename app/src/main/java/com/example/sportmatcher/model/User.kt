package com.example.sportmatcher.model


data class User(
    var uid: String? = "",
    var email: String ="",
    var firstName: String? = "",
    var lastName: String? = "",
    var friends : HashMap<String, User>? = null
){

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
            "friends" to friends
        )
    }
}