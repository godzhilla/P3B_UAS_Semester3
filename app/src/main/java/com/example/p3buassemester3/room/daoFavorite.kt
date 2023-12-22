package com.example.p3buassemester3.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.p3buassemester3.dataclass.FavModel

@Dao
interface daoFavorite {
@Insert
fun insert(favModel: FavModel)

@Delete
fun delete(favModel: FavModel)

@Update
fun update(favModel: FavModel)

@Query("SELECT * FROM favorite_table WHERE uid == :uid")
suspend fun getAllFavByUser(uid: String) : List<FavModel>

@Query("DELETE FROM favorite_table")
fun deleteAllData()
}