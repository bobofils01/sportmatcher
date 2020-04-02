package com.example.sportmatcher.model.sport

import android.os.Parcel
import android.os.Parcelable

data class Session(
    var uid : String? = "",
    val pitch: String? = null,
    val totalNbPlayers: Int = 0,
    val nbPlayersSigned: Int = 0,
    val price: Double = 0.0) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    )

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "pitch" to pitch,
            "totalNbPlayers" to totalNbPlayers,
            "nbPlayersSigned" to nbPlayersSigned,
            "price" to price
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(pitch)
        parcel.writeInt(totalNbPlayers)
        parcel.writeInt(nbPlayersSigned)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Session> {
        override fun createFromParcel(parcel: Parcel): Session {
            return Session(parcel)
        }

        override fun newArray(size: Int): Array<Session?> {
            return arrayOfNulls(size)
        }
    }
}