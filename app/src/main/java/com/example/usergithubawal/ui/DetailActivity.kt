package com.example.usergithubawal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.usergithubawal.adapter.PageAdapter
import com.example.usergithubawal.R
import com.example.usergithubawal.databinding.ActivityDetailBinding
import com.example.usergithubawal.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    companion object{
        private val JUDUL = listOf(
            R.string.Follower,
            R.string.Following
        )
    }

    private lateinit var binding : ActivityDetailBinding
    private val viewModelDetailUser by  viewModels<DetailViewModel>()
    private lateinit var username: String
    private lateinit var avatarUrl: String
    var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Github User"

        dataUser()
        getViewPager()
        saveFavorite()
        checkFavorite()


    }
    private fun saveFavorite(){
        val toggleButton = findViewById<ImageView>(R.id.toggle_favorite)
        binding.toggleFavorite.setOnClickListener{
            if (check){
                toggleButton.setImageResource(R.drawable.ic_favorite_grey)
                viewModelDetailUser.deleteUser(username)
                check = false
            }else{
                toggleButton.setImageResource(R.drawable.ic_favorite_red)
                viewModelDetailUser.insertUser(username, avatarUrl)
                check = true

            }
        }

    }


    private fun checkFavorite(){
        val toggleButton = findViewById<ImageView>(R.id.toggle_favorite)
        CoroutineScope(Dispatchers.IO).launch {
            val cekFavorite = viewModelDetailUser.checkUser(username)
            withContext(Dispatchers.Main){
                if (cekFavorite != null){
                    if(cekFavorite > 0){
                        toggleButton.setImageResource(R.drawable.ic_favorite_red)
                        check = true
                    }else{
                        toggleButton.setImageResource(R.drawable.ic_favorite_grey)
                        check = false
                    }
                }
            }
        }
    }

    private fun dataUser() {
        username = intent.getStringExtra("username")!!
        avatarUrl = intent.getStringExtra("avatarUrl")!!

        viewModelDetailUser.isLoading.observe(this) {
            showLoading(it)
        }
        viewModelDetailUser.userDetail(username)
        viewModelDetailUser.detailUser.observe(this) { data ->
            Glide.with(this)
                .load(data.avatarUrl)
                .fitCenter()
                .into(binding.ivAvatar)

            binding.tvUsername.text = data.login
            binding.name.text = data.name

            val follower = getString(R.string.number_follower, data.followers.toString())
            val following = getString(R.string.number_following, data.following.toString())

            binding.followerCount.text = follower
            binding.followingCount.text = following
        }
    }

    private fun getViewPager(){
        val adapter = PageAdapter(this, username)
        binding.viewPager.adapter =  adapter

        TabLayoutMediator(binding.tabs, binding.viewPager){tablayout, position ->
            val text= this.getString(JUDUL[position])
            tablayout.text = text

        }.attach()
    }

    private fun showLoading(isLoading: Boolean){
        val visibility = if (isLoading) View.GONE else View.VISIBLE
        with(binding){
            tvUsername.visibility = visibility
            name.visibility= visibility
            followerCount.visibility= visibility
            followingCount.visibility= visibility
            progressBar.visibility= if (isLoading) View.VISIBLE else View.GONE
        }
    }
}