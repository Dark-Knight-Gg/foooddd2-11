package com.example.foodapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foodapp2.databinding.CustomRecyclerviewAdminBinding
import com.example.foodapp2.model.Food
import javax.security.auth.callback.Callback

class AdminAdapter(private val callback:(Int) -> Unit) :
    BaseAdapter<Food, AdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRecyclerviewAdminBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: CustomRecyclerviewAdminBinding) :
        BaseViewHolder<Food>(binding) {
        override fun bindData(data: Food) {
            binding.customRecyclerViewModel.name
            binding.adminImgDelete.setOnClickListener() {
                callback.invoke(data.id)
            }
        }

    }

}