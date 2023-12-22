package com.example.p3buassemester3.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.p3buassemester3.dataclass.FavModel
import androidx.room.Room.databaseBuilder

@Database(entities = [FavModel::class], version = 1, exportSchema = false)

abstract class database : RoomDatabase(){
    abstract fun daoFavorite() : daoFavorite?

    companion object{
        @Volatile
        private var INSTANCE: database? = null
        fun getDatabase(context: Context) : database? {
            if (INSTANCE == null){
                synchronized(database::class.java){
                    INSTANCE = databaseBuilder(context.applicationContext,
                        database::class.java, "database").build()
                }
            }
            return INSTANCE
        }
    }
}