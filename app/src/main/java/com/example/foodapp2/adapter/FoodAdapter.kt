package com.example.foodapp2.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foodapp2.databinding.CustomRecyclerviewfoodBinding
import com.example.foodapp2.model.Food
import com.example.foodapp2.model.FoodOder


class FoodAdapter(private var callback: (FoodOder) -> Unit) :
    BaseAdapter<Food, FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRecyclerviewfoodBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: CustomRecyclerviewfoodBinding) :
        BaseViewHolder<Food>(binding) {
        override fun bindData(data: Food) {
            binding.food = data
            binding.customBtn.setOnClickListener(){
                val foodOder  = FoodOder(data.id,data.name,data.describe,data.price,data.picture,binding.customEdtCount.toString().toInt())
                callback.invoke(foodOder)
            }
        }

    }
}