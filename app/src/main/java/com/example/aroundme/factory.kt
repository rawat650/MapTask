package com.example.aroundme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aroundme.viemodel.RestaurantViewModel

class factory (val repo: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repo) as T
    }
}