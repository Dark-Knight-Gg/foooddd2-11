package com.example.foodapp2.model

import com.example.foodapp2.BR
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable

data class User(var username:String? , var password : String?){
//    var username: String? = null
//    @Bindable get() = field
//    set(username){
//        field = username
//        notifyPropertyChanged(BR.username)
//    }
//    var password: String? = null
//    @Bindable get() = field
//    set(password){
//        field = password
//        notifyPropertyChanged(BR.password)
//    }
}