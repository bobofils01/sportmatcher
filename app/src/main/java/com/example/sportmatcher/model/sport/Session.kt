package com.example.sportmatcher.model.sport

data class Session(
    var uid : String? = "",
    val pitch: String? = null,
    val totalNbPlayers: Int = 0,
    val nbPlayersSigned: Int = 0,
    val price: Double = 0.0) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "pitch" to pitch,
            "totalNbPlayers" to totalNbPlayers,
            "nbPlayersSigned" to nbPlayersSigned,
            "price" to price
        )
    }
}