package com.example.foodapp2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foodapp2.databinding.CustomRecyclerviewHistoryBinding
import com.example.foodapp2.model.History

class HistoryAdapter(
) : BaseAdapter<History, HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomRecyclerviewHistoryBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: CustomRecyclerviewHistoryBinding) :
        BaseViewHolder<History>(binding) {
        override fun bindData(data: History) {
            binding.listHistory = data
        }
    }



}