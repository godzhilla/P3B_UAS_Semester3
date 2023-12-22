package com.example.p3buassemester3.dataclass

import com.google.firebase.firestore.Exclude

data class User(
    var id: String = "",
    var email: String = "",
    var password: String = "",
    var username: String = "",
    var role: String = "user"
)
