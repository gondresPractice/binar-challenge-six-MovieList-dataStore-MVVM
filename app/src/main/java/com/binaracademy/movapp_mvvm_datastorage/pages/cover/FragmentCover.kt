package com.binaracademy.movapp_mvvm_datastorage.pages.cover

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.binaracademy.movapp_mvvm_datastorage.R
import com.binaracademy.movapp_mvvm_datastorage.databinding.ActivityCoverBinding
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentRegisterBinding


class FragmentCover : Fragment() {

    lateinit var userManager : com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
    var isLoggedIn : Boolean = false
    private lateinit var binding : ActivityCoverBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager(requireContext())
        checkLoginStatus()


    }

    private fun checkLoginStatus() {
        userManager.isLoggedInFlow.asLiveData().observe(requireActivity(),{
            isLoggedIn = it
            var handler = Handler()
            if (it == false){
                handler.postDelayed(
                    {
                        findNavController().navigate(FragmentCoverDirections.actionFragmentCoverToLoginFragment())
                    },3000
                )
            }else{
                findNavController().navigate(FragmentCoverDirections.actionFragmentCoverToHomeFragment())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ActivityCoverBinding.inflate(inflater,container,false)
        return binding.root }

}