package com.example.usergithubawal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usergithubawal.R
import com.example.usergithubawal.adapter.UserAdapter
import com.example.usergithubawal.databinding.ActivityDetailBinding
import com.example.usergithubawal.databinding.ActivityFavoriteBinding
import com.example.usergithubawal.response.ItemsItem

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<FavoriteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter(emptyList())
        binding.favorite.layoutManager = LinearLayoutManager(this)
        binding.favorite.adapter = adapter
        viewModel.getUser()?.observe(this){user ->
            val listFavorite = mutableListOf<ItemsItem>()
            user.map { data->
                val dataFavorite = ItemsItem(login = data.username, avatarUrl = data.avatarUrl)
                listFavorite.add(dataFavorite)
            }
            adapter.setData(listFavorite)
        }
    }

//    fun showFavorite(){
//        adapter = UserAdapter(emptyList())
//        binding.favorite.layoutManager = LinearLayoutManager(this)
//        binding.favorite.adapter = adapter
//        viewModel.getUser()?.observe(this){user ->
//            val listFavorite = mutableListOf<ItemsItem>()
//            user.map { data->
//                val dataFavorite = ItemsItem(login = data.username, avatarUrl = data.avatarUrl)
//                listFavorite.add(dataFavorite)
//            }
//            adapter.setData(listFavorite)
//        }
//    }

}