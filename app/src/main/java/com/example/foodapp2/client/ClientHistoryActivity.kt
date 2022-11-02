package com.example.foodapp2.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp2.R
import com.example.foodapp2.adapter.HistoryAdapter
import com.example.foodapp2.database.Database
import com.example.foodapp2.databinding.ActivityClientHistoryBinding
import com.example.foodapp2.model.History

class ClientHistoryActivity : AppCompatActivity() {
    private lateinit var database: Database
    private val listHistory = ArrayList<History>()
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: ActivityClientHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_history)
        getData()
        initListener()
        adapter = HistoryAdapter()
        binding.avt6RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.avt6RecyclerView.adapter = adapter
    }

    private fun initListener() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_history)
        binding.avt6ImgBack.setOnClickListener {
            val intent = Intent(this, ClientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        database = Database.getInstance(this)
        listHistory.clear()
        listHistory.addAll(database.getHistory())
    }
}