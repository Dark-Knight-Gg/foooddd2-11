package com.example.foodapp2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private val _selectedItem = MutableLiveData<FoodOder>()
    val selectedItem: LiveData<FoodOder> get() = _selectedItem

    fun sendFood(foodOder: FoodOder) {
        _selectedItem.value = foodOder
    }
}