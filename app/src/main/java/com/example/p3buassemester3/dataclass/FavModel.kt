package com.example.p3buassemester3.dataclass

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavModel(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val destinationName : String,

    @ColumnInfo(name = "description")
    val destinationDesc : String,

    @ColumnInfo(name = "uid")
    val uid : String

)
