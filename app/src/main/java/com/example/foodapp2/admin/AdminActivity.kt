package com.example.foodapp2.admin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp2.R
import com.example.foodapp2.adapter.AdminAdapter
import com.example.foodapp2.database.Database
import com.example.foodapp2.databinding.ActivityAdminBinding
import com.example.foodapp2.login.LoginActivity
import com.example.foodapp2.model.Food

class AdminActivity : AppCompatActivity() {
    private val database by lazy { Database.getInstance(this) }
    private val listFood = ArrayList<Food>()
    private val adp by lazy { AdminAdapter(){diaLogDelete(it)} }
    private val adminViewModel by viewModels<AdminViewModel>()
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin)
        binding.adminViewModel = adminViewModel
        binding.adminRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.adminRecyclerview.adapter = adp
        onClickBack()
        onClickAdd()
        getData()
    }

//    private fun initListener() {
//        binding.adminIgBack.setOnClickListener {
//            intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//        binding.adminBtnAdd.setOnClickListener {
//            intent = Intent(this, AdminAddActivity::class.java)
//            startActivity(intent)
//        }
//    }
    private fun onClickBack(){
        adminViewModel.navigateToLogin.observe(this){
            it.getContentIfNotHandled()?.let{
                intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun onClickAdd(){
        adminViewModel.navigateToAddAdmin.observe(this){
            it.getContentIfNotHandled()?.let{
                intent = Intent(this, AdminAddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getData() {
        listFood.clear()
        listFood.addAll(database.getFood())
        adp.setData(listFood)
       // adp.notifyDataSetChanged()
    }

    private fun diaLogDelete(position: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_delete)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialogDeleteBtnYes: Button = dialog.findViewById(R.id.dialogDeleteBtnYes)
        val dialogDeleteBtnNo: Button = dialog.findViewById(R.id.dialogDeleteBtnNo)
        dialogDeleteBtnNo.setOnClickListener {
            Toast.makeText(this, "Đã hủy!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialogDeleteBtnYes.setOnClickListener {
            database.deleteById(position)
            Toast.makeText(this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show()
            getData()
            dialog.dismiss()
        }
        dialog.show()
    }
}