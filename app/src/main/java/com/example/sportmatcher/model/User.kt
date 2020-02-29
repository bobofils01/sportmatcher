package com.example.sportmatcher.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = "",
    var email: String ="",
    var firstName: String? = "",
    var lastName: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName
        )
    }
}