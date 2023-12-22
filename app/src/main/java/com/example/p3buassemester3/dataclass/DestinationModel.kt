package com.example.p3buassemester3.dataclass

import androidx.annotation.DrawableRes
import com.google.firebase.firestore.Exclude

data class DestinationModel(
    var id: String = "",
    val destinationName : String = "",
    val destinationDesc : String = "",

    // untuk favorite firebase, jangan dipakai kalau bukan di tabel favorit firbase
    var userId : String = ""
)
