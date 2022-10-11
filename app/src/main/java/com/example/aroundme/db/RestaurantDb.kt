package com.example.aroundme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aroundme.model.RestaurantData

@Database(entities = [RestaurantData::class], version = 1)

abstract class RestaurantDb: RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    companion object{
        private var instance:RestaurantDb? = null
        private val LOCK =Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,RestaurantDb::class.java,
                "location_db.db"
            ).build()
    }
}