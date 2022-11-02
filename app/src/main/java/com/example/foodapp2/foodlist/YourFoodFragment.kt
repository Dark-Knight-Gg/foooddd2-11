package com.example.foodapp2.foodlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp2.R
import com.example.foodapp2.adapter.FoodAdapterB
import com.example.foodapp2.database.Database
import com.example.foodapp2.databinding.FragmentYourFoodBinding
import com.example.foodapp2.model.FoodOder
import com.example.foodapp2.model.ItemViewModel
import java.util.EnumSet.of

class YourFoodFragment : Fragment() {

    private val viewModel by viewModels<ItemViewModel>()

    private val adapter by lazy {
        FoodAdapterB() { deleteFood(it) }
    }
    private val database by lazy { Database.getInstance(requireActivity()) }
    private lateinit var binding: FragmentYourFoodBinding
    private val listFoodOder = ArrayList<FoodOder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYourFoodBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragTwoRecyclerView.layoutManager = LinearLayoutManager(activity)
            fragTwoRecyclerView.adapter = adapter
            fragTwoImgPay.setOnClickListener {
                dialogPay()
            }
        }
        viewModel.selectedItem.observe(viewLifecycleOwner) {
            val item = FoodOder(it.id, it.name, it.describe, it.price, it.picture, it.count)
            adapter.addItem(item)
        }
    }

    private fun deleteFood(position: Int) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_delete_food)
        val dialogDeleteBtnYes: Button = dialog.findViewById(R.id.dialogDeleteBtnYes)
        val dialogDeleteBtnNo: Button = dialog.findViewById(R.id.dialogDeleteBtnNo)
        dialogDeleteBtnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogDeleteBtnYes.setOnClickListener {
            listFoodOder.removeAt(position)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun dialogPay() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_pay)
        val dialogPayTxtMoney: TextView = dialog.findViewById(R.id.dialogPayTxtMoney)
        val dialogPayBtnOk: Button = dialog.findViewById(R.id.dialogPayBtnOk)
        var money = 0.0
        val n = listFoodOder.size
        for (i in 0 until n) {
            money += (listFoodOder[i].price * listFoodOder[i].count)
        }
        dialogPayTxtMoney.text = money.toString()
        dialogPayBtnOk.setOnClickListener {
            getHistory()
            listFoodOder.clear()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getHistory() {
        database.getYourHistory(listFoodOder)
    }
}