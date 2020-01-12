package com.vp.favorites.model

import com.bumptech.glide.Glide
import com.dgr.data.db.model.Movie
import com.vp.favorites.R
import com.vp.favorites.databinding.LayoutMovieItemBinding
import com.xwray.groupie.databinding.BindableItem

class MovieItem(
        val movie: Movie
) : BindableItem<LayoutMovieItemBinding>() {
    override fun getLayout(): Int = R.layout.layout_movie_item

    override fun bind(viewBinding: LayoutMovieItemBinding, position: Int) {
        viewBinding.movie = movie
        Glide.with(viewBinding.poster)
                .load(movie.poster)
                .into(viewBinding.poster)
    }
}