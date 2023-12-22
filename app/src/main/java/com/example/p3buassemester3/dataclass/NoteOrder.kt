package com.example.p3buassemester3.dataclass

data class NoteOrder(

    var id: String? = "",
    val userId : String = "",
    val kotaAsal: String = "",
    val kotaTujuan: String = "",
    val keberangkatan: String = "",
    val totalHarga: Int = 0

) {
    companion object {
        const val FIELD_USER_ID = "userId"
    }
}

