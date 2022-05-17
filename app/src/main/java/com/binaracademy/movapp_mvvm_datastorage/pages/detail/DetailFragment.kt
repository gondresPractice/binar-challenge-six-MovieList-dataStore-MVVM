package com.binaracademy.movapp_mvvm_datastorage.pages.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.binaracademy.movapp_mvvm_datastorage.R
import com.binaracademy.movapp_mvvm_datastorage.database.create_db.UserDatabase
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide


class DetailFragment : Fragment() {

    private val arguments: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private var user_db: UserDatabase? = null
    var movieId: Int = 0
    var checkFav = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_db = UserDatabase.getInstance(requireContext())


        bindingItemView()
        checkFavorite()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToHomeFragment())
        }
        binding.ivFavorite.setOnClickListener {

         if(checkFavorite() == true){
             Thread {
                 val result = user_db?.FavoriteDao()?.deleteFavorite(movieId)
                 activity?.runOnUiThread {
                     if (result != 0) {
                         binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                     }
                 }
             }.start()

         }else{
             val favoriteList = Favorite(
                 arguments.detail.id,
                 arguments.detail.title,
                 arguments.detail.subtitle,
                 arguments.detail.overview,
                 arguments.detail.date,
                 arguments.detail.image,
                 arguments.detail.rating,
                 arguments.detail.popularity
             )
             Thread {
                 val result = user_db?.FavoriteDao()?.insertFavorite(favoriteList)
                 activity?.runOnUiThread {
                     if (result != 0.toLong()) {
                         Toast.makeText(
                             requireContext(),
                             "Sukses Menambahkan ke Favorite",
                             Toast.LENGTH_SHORT
                         ).show()
                         Log.d("Insert", favoriteList.toString())
                         binding.ivFavorite.setImageResource(R.drawable.ic_favorite_saved)

//                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                     } else {
                         Toast.makeText(
                             requireContext(),
                             "Gagal menambahkan ke Favorite",
                             Toast.LENGTH_LONG
                         ).show()
                     }
                 }
             }.start()
         }

        }
    }

    private fun checkFavorite() : Boolean {

        Thread {
            val result = user_db?.FavoriteDao()?.getDetailFavorite(movieId)
            activity?.runOnUiThread {
                if (result != 0) {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_saved)
                    checkFav = true
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    checkFav = false
                }
            }
        }.start()

        return checkFav
    }

    private fun bindingItemView() {

        movieId = arguments.detail.id
        var title = arguments.detail.title
        var subtitle = arguments.detail.subtitle
        var overview = arguments.detail.overview
        var date = arguments.detail.date
        var image = arguments.detail.image
        var rating = arguments.detail.rating
        var popularity = arguments.detail.popularity

        var rate = rating.toFloat()
        "%.0f".format(rate)

        print("ID $movieId")

        binding.proggRate.setProgress(rating.toInt() * 10)
        binding.tvTitle.setText(title)
        binding.tvOverview.setText(overview)
        binding.tvDate.setText(date)
        binding.tvRate.setText(rate.toString())
        binding.tvPopularity.setText(popularity)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w780/" + image)
            .into(binding.ivPoster)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}