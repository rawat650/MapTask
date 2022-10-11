package com.example.aroundme.viemodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aroundme.Repository
import com.example.aroundme.model.RestaurantData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RestaurantViewModel(val repository: Repository): ViewModel() {


    fun getData() = repository.getLocation()

    fun insertdata(data: RestaurantData)=
        viewModelScope.launch(Dispatchers.IO) {
            repository.dataInsert(data)
        }
    fun deleteData(data: RestaurantData) = viewModelScope.launch {
        repository.deleteData(data)
    }
}
