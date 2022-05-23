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
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private val arguments: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel : DetailViewModel by viewModel()
    private var user_db: UserDatabase? = null
    var movieId: Int = 0
    var checkFav = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingItemView()
        checkFavorite()
        movieId = arguments.detail.id
        Log.d("ID Movie", movieId.toString())

        binding.ivBack.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToHomeFragment())
        }
        binding.ivFavorite.setOnClickListener {

         if(checkFav == false){

            detailViewModel.deleteFromFavorite(movieId)
             detailViewModel.checkStatDelete().observe(viewLifecycleOwner){
                 if (it==true){
                     binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                 }
             }
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
             detailViewModel.insertToFavorite(favoriteList)
             detailViewModel.checkStatInsert().observe(viewLifecycleOwner){
                 if(it == true){
                     Log.d("Insert", favoriteList.toString())
                     binding.ivFavorite.setImageResource(R.drawable.ic_favorite_saved)
                 }else{
                     Log.d("Gagal menambahkan", favoriteList.toString())
                 }
             }
         }

        }
    }

    private fun checkFavorite() : Boolean {

        detailViewModel.checkFavoriteData(movieId)
        detailViewModel.checkStatFavorite().observe(viewLifecycleOwner){
            if(it == true){
                checkFav = true
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_saved)
            }else{
                checkFav = false
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            }
            Log.d("Chek Fav",it.toString())
        }


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

        Log.d("ID Movie", movieId.toString())

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