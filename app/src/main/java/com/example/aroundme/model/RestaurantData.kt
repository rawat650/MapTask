package com.example.aroundme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurantData")

data class RestaurantData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name:String,val address:String,val image:Int,val latitude:Double,val longitude:Double)
