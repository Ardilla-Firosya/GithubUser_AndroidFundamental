package com.example.usergithubawal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usergithubawal.adapter.UserAdapter
import com.example.usergithubawal.databinding.FragmentFollowBinding
import com.example.usergithubawal.viewmodel.FollowViewModel

class fragment_follow : Fragment() {
    private lateinit var _binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private var position: Int = 0

    companion object{
        var FOLLOW_NAME: String =" "
        var FOLLOW_POSITION= " section_number"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(FOLLOW_NAME)
            username = it.getString(FOLLOW_POSITION)!!
        }
        val layoutManager = LinearLayoutManager(requireContext())
        _binding.followRv.layoutManager = layoutManager
        if(isAdded && !isDetached){
            val viewModelFollow = ViewModelProvider(
                this, ViewModelProvider.NewInstanceFactory()
            )[FollowViewModel::class.java]
            viewModelFollow.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
            viewModelFollow.followModel.observe(viewLifecycleOwner){follow ->
                if(follow !=null){
                    adapter = UserAdapter(follow)
                    _binding.followRv.adapter = adapter
                }
            }
            if (position ==1){
                viewModelFollow.getFollow(username, "Follower")
            }else{
                viewModelFollow.getFollow(username, "Following")
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        _binding.followRv.visibility = if (isLoading) View.GONE else View.VISIBLE
        _binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return _binding.root
    }

    }
