package com.example.foodapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodapp2.event.Event

class CustomRecyclerViewModel(application: Application) : AndroidViewModel(application) {
    private val _name = MutableLiveData<String>()
    val name : MutableLiveData<String> get() = _name
    private val _describe = MutableLiveData<String>()
    val describe : MutableLiveData<String> get() = _describe
    private  val _price = MutableLiveData<Double>()
    val price : MutableLiveData<Double> get() = _price
    private val _picture = MutableLiveData<ByteArray>()
    val picture : MutableLiveData<ByteArray> get() = _picture
    private val _isShowDeleteDialog = MutableLiveData<Event<Boolean>>()
    val isShowDeleteDialog : LiveData<Event<Boolean>> get() =_isShowDeleteDialog
    fun onClickDelete(){
        _isShowDeleteDialog.value= Event(true)
    }
}