package com.binaracademy.movapp_mvvm_datastorage.pages.favorite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binaracademy.movapp_mvvm_datastorage.adapter.FavoriteAdapter
import com.binaracademy.movapp_mvvm_datastorage.database.create_db.UserDatabase
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private val sharedPref = "sharedpreferences"
    private val favoriteViewModel : FavoriteViewModel by viewModel()
    var listFavorite : List<Favorite>? = null
    var adapter : FavoriteAdapter?=null
    private var user_db : UserDatabase? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchFavorite()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToHomeFragment())
        }

    }

    private fun fetchFavorite() {
        favoriteViewModel.getFavoriteList()
        favoriteViewModel.getFavorite().observe(viewLifecycleOwner){
            adapter = FavoriteAdapter(it)
            val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
                   binding.rvMain.layoutManager = layoutManager
                   binding.rvMain.adapter = adapter
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root }

}