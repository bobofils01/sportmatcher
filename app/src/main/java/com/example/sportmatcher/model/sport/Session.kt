package com.example.sportmatcher.model.sport


import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Session(
    var uid : String? = "",
    val pitch: String? = null,
    val title: String?="",
    val description: String?="",
    val date: String?="",
    val time: String?="",
    val maxNbPlayers: Int? = -1,
    val nbPlayersSigned: Int? = 0,
    val pricePlayer: Double? = 0.0) : Parcelable {

}