package com.example.sportmatcher.model.sport

import android.os.Parcel
import android.os.Parcelable

data class Pitch(var uid: String? = "",
                 val name :String? ="sportName",
                 val description : String? = "",
                 val pitchPicture : String? = "",
                 val address: String? = "",
                 val sport: String? = "football",
                 val latitude: Double? = 0.0,
                 val longitude: Double? = 0.0,
                 val sessions: HashMap<String, Boolean>? = null) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble() as? Double?,
        parcel.readDouble() as? Double?,
        parcel.readHashMap(HashMap::class.java.classLoader) as HashMap<String, Boolean>?
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.apply {
            writeString(uid)
            writeString(name)
            writeString(description)
            writeString(pitchPicture)
            writeString(address)
            writeString(sport)
            latitude?.let { writeDouble(it) }
            longitude?.let { writeDouble(it) }
            writeMap(sessions as Map<String, Boolean>?)
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "description" to description,
            "pitchPicture" to pitchPicture,
            "address" to address?.toLowerCase(),
            "sport" to sport?.toLowerCase(),
            "latitude" to latitude,
            "longitude" to longitude,
            "sessions" to sessions
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pitch> {
        override fun createFromParcel(parcel: Parcel): Pitch {
            return Pitch(parcel)
        }

        override fun newArray(size: Int): Array<Pitch?> {
            return arrayOfNulls(size)
        }
    }
}