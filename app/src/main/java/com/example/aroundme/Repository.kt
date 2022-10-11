package com.example.aroundme

import com.example.aroundme.db.RestaurantDb
import com.example.aroundme.model.RestaurantData


class Repository(val restaurantdb: RestaurantDb) {


    fun getLocation() = restaurantdb.restaurantDao().getData()

    suspend fun dataInsert(data : RestaurantData)= restaurantdb.restaurantDao().addRestaurant(data)
    suspend fun deleteData(data: RestaurantData) = restaurantdb.restaurantDao().deleteRestaurant(data)
}
