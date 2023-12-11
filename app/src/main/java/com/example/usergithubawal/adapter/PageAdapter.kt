package com.example.usergithubawal.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.usergithubawal.ui.fragment_follow

class PageAdapter (activity: AppCompatActivity, private val name: String): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragment_follow()
        fragment.arguments = Bundle().apply {
            putInt(fragment_follow.FOLLOW_NAME, position + 1)
            putString(fragment_follow.FOLLOW_POSITION, name)
        }
        return fragment
    }
}