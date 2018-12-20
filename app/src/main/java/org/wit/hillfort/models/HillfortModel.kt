package org.wit.hillfort.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                          var fbId : String = "",
                          var title: String = "",
                          var description: String = "",
                          @Embedded var images: ArrayList<String> = ArrayList(),
                          var visited: Boolean = false,
                          @Embedded var location : Location = Location(),
                          var notes: String = "",
                          var rating: Float = 0.0f,
                          var dayVisited: Int = 1,
                          var monthVisited: Int = 1,
                          var yearVisited: Int = 2018): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


