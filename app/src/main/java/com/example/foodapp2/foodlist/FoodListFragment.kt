package com.example.foodapp2.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp2.adapter.FoodAdapter
import com.example.foodapp2.database.Database
import com.example.foodapp2.databinding.FragmentFoodListBinding
import com.example.foodapp2.model.Food
import com.example.foodapp2.model.FoodOder
import com.example.foodapp2.model.ItemViewModel

class FoodListFragment : Fragment() {
    private lateinit var binding: FragmentFoodListBinding
    private var listFood = ArrayList<Food>()
    private val foodAdapter by lazy { FoodAdapter(){sendData(it)} }
    private lateinit var viewModel: ItemViewModel
    private val database: Database by lazy { Database.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            fragOneRecyclerView.layoutManager = LinearLayoutManager(activity)
            fragOneRecyclerView.adapter = foodAdapter
            getData()
        }

    }

    private fun getData() {
        listFood.clear()
        listFood.addAll(database.getFoodList())
        foodAdapter.setData(listFood)
    }

    private fun sendData(foodOder: FoodOder) {
        viewModel = ViewModelProvider(requireActivity())[ItemViewModel::class.java]
        viewModel.sendFood(foodOder)
    }
}