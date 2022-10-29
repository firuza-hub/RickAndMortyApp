package com.example.rickandmortybyfsa.data.remote.models

import android.os.Parcel
import android.os.Parcelable

data class CharacterDetails(
    val created: String?,
    val episode: List<String>?,
    val gender: String?,
    val id: Int,
    val image: String?,
    val location: Location?,
    val name: String?,
    val origin: Origin?,
    val species: String?,
    val status: String?,
    val type: String?,
    val url: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Location::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Origin::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created)
        parcel.writeStringList(episode)
        parcel.writeString(gender)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeParcelable(location, flags)
        parcel.writeString(name)
        parcel.writeParcelable(origin, flags)
        parcel.writeString(species)
        parcel.writeString(status)
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterDetails> {
        override fun createFromParcel(parcel: Parcel): CharacterDetails {
            return CharacterDetails(parcel)
        }

        override fun newArray(size: Int): Array<CharacterDetails?> {
            return arrayOfNulls(size)
        }
    }
}