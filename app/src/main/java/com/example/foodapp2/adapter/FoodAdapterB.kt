package com.example.foodapp2.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foodapp2.databinding.CustomRecyclerviewfood2Binding
import com.example.foodapp2.model.FoodOder

class FoodAdapterB(
    private var callback: (Int) -> Unit
) : BaseAdapter<FoodOder, FoodAdapterB.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRecyclerviewfood2Binding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: CustomRecyclerviewfood2Binding) :
        BaseViewHolder<FoodOder>(binding) {
        override fun bindData(data: FoodOder) {
            binding.foodOrder = data
            binding.frag2ImgDelete.setOnClickListener {
                callback.invoke(adapterPosition)
            }
        }
    }


}