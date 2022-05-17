package com.binaracademy.movapp_mvvm_datastorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.Favorite
import com.binaracademy.movapp_mvvm_datastorage.databinding.ItemRecycleviewFavoriteBinding
import com.binaracademy.movapp_mvvm_datastorage.model.DetailMovieModel
import com.binaracademy.movapp_mvvm_datastorage.model.Result
import com.binaracademy.movapp_mvvm_datastorage.pages.favorite.FavoriteFragmentDirections
import com.binaracademy.movapp_mvvm_datastorage.pages.home.HomeFragmentDirections
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val item: List<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.MainViewHolder>() {


    class MainViewHolder(val binding: ItemRecycleviewFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRecycleviewFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.tvTitle.text = item[position].title.toString()
        holder.binding.tvDate.text = item[position].date
        holder.binding.tvRate.text = item[position].rating.toString()


        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w780/"+item[position].image)
            .into(holder.binding.ivPosterImage);


        holder.itemView.setOnClickListener {
            var movieId = item[position].id!!.toInt()
            var name = item[position].title.toString()
            var subtitle = item[position].popularity.toString()
            var overview = item[position].overview.toString()
            var date = item[position].date.toString()
            var images = item[position].image.toString()
            var rating = item[position].rating.toString()
            var popularity = item[position].popularity.toString()


            var detail = DetailMovieModel(
                movieId,name, subtitle,overview,date,images,rating.toDouble(),popularity)
            it.findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(detail))
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
}