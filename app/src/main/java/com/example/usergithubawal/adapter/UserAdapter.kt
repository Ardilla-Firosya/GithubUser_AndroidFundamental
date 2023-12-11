package com.example.usergithubawal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usergithubawal.databinding.UserListBinding
import com.example.usergithubawal.response.ItemsItem
import com.example.usergithubawal.ui.DetailActivity

class UserAdapter(var dataItem: List<ItemsItem>):
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: UserListBinding):
    RecyclerView.ViewHolder(binding.root){
        fun binding(data: ItemsItem){
            binding.tvItemName.text = data.login

            Glide.with(itemView.context)
                .load(data.avatarUrl)
                .fitCenter()
                .into(binding.imgItemPhoto)

            binding.cardView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("username", data.login)
                intent.putExtra("avatarUrl", data.avatarUrl)
                itemView.context.startActivity(intent)
            }


        }
    }
    fun setData(data: List<ItemsItem>){
        dataItem = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun getItemCount(): Int {
        return dataItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataUser = dataItem[position]
        holder.binding(dataUser)

        }
    }

