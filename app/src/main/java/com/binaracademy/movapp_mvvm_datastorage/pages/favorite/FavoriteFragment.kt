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


class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private val sharedPref = "sharedpreferences"
    var listFavorite : List<Favorite>? = null
    var adapter : FavoriteAdapter?=null
    private var user_db : UserDatabase? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences(sharedPref, Context.MODE_PRIVATE)

        user_db = UserDatabase.getInstance(requireContext())

        fetchFavorite()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToHomeFragment())
        }

    }

    private fun fetchFavorite() {
       Thread{
            listFavorite = user_db?.FavoriteDao()?.getFavorite()
           activity?.runOnUiThread {
               listFavorite?.let {
                   if(it==null){
                       binding.progressBar.visibility = View.VISIBLE
                   }else{
                       binding.progressBar.visibility = View.GONE
                   }
                   adapter = FavoriteAdapter(it)
                   val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
                   binding.rvMain.layoutManager = layoutManager
                   binding.rvMain.adapter = adapter
               }
           }
       }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root }

}