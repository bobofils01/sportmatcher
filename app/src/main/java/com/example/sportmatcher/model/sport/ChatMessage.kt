package com.example.sportmatcher.model.sport

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatMessage(var uuid: String?="",
                  val sessionID :String?="",
                  val senderName: String="",
                  val senderUUID:String="",
                  val message: String="",
                  val timestamp: Long?=null
                  ){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uuid,
            "sessionID" to sessionID,
            "senderName" to senderName,
            "senderUUID" to senderUUID,
            "message" to message,
            "timestamp" to timestamp
        )
    }

}
