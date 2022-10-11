package com.example.aroundme.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aroundme.model.RestaurantData
@Dao
interface RestaurantDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addRestaurant(data: RestaurantData)

        @Query("select * From  restaurantData")
        fun getData(): LiveData<List<RestaurantData>>

        @Delete
        suspend fun deleteRestaurant(data:RestaurantData)
    }
