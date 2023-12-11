package com.example.usergithubawal.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usergithubawal.R
import com.example.usergithubawal.adapter.UserAdapter
import com.example.usergithubawal.databinding.ActivityMainBinding
import com.example.usergithubawal.viewmodel.UserViewModel
import com.setting.SettingPreferences
import com.setting.SettingViewModel
import com.setting.SettingViewModelFactory
import com.setting.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var adapter: UserAdapter

    private fun showLoading(isLoading:Boolean){
        _binding.userRv.visibility= if(isLoading) View.GONE else View.VISIBLE
        _binding.progressBar.visibility= if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_setting ->{
                var intentSetting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intentSetting)
            }
            R.id.action_favorite ->{
                var intentFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentFavorite)
            }

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)

        viewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        _binding.userRv.layoutManager = layoutManager
        with(_binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    userViewModel.setName(searchBar.text.toString())
                    false
                }
        }
        userViewModel.datalist.observe(this){data ->
            if(data != null){
                adapter = UserAdapter(data)
                _binding.userRv.adapter = adapter
            }
        }
        userViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }
}