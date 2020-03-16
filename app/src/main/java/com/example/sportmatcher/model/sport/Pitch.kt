package com.example.sportmatcher.model.sport

data class Pitch(var uid: String? = "",
                 val name :String? ="sportName",
                 val description : String? = "",
                 val pitchPicture : String? = "",
                 val address: String? = "",
                 val sport: String? = "football",
                 val latitude: Double? = 0.0,
                 val longitude: Double? = 0.0) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "description" to description,
            "pitchPicture" to pitchPicture,
            "address" to address?.toLowerCase(),
            "sport" to sport?.toLowerCase(),
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}