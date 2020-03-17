package com.example.sportmatcher.model.sport

data class Session(
    var uid : String? = "",
    val idPitch: Pitch? = null,
    val totalNbPlayers: Int = 0,
    val nbPlayersSigned: Int = 0,
    val price: Double = 0.0) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "idPitch" to idPitch?.uid,
            "totalNbPlayers" to totalNbPlayers,
            "nbPlayersSigned" to nbPlayersSigned,
            "price" to price
        )
    }
}