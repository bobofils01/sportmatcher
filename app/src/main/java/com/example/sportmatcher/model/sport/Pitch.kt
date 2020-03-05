package com.example.sportmatcher.model.sport

data class Pitch(var uid: String? = "", val address: String? = "", val sport: String? = "football", val latitude: Double, val longitude: Double) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "address" to address?.toLowerCase(),
            "sport" to sport?.toLowerCase(),
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}