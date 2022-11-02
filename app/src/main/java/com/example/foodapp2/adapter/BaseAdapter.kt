package com.example.foodapp2.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA, VH : BaseViewHolder<DATA>>(
//    initData: List<DATA>
) : RecyclerView.Adapter<VH>() {
    private val data = mutableListOf<DATA>()

//    init {
//        data.clear()
//        data.addAll(initData)
//    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<DATA>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addItem(item: DATA) {
        data.add(item)
        notifyItemInserted(data.size - 1)
    }
    fun addItem(item: DATA, position: Int) {
        data.add(position, item)
        notifyItemInserted(position)
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = data[position]
        holder.bindData(item)
    }

    override fun getItemCount() = data.size
}

abstract class BaseViewHolder<DATA>(
    itemView: ViewDataBinding
) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bindData(data: DATA)
}