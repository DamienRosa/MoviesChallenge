package com.vp.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgr.data.db.model.Movie
import com.dgr.data.db.repository.MovieRepository
import com.vp.favorites.model.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
        private val repository: MovieRepository
) : ViewModel() {

    private val liveData = MutableLiveData<List<MovieItem>>()
    private val errorText = MutableLiveData<Boolean>()
    private val progressBar = MutableLiveData<Boolean>()

    fun observeMovies(): LiveData<List<MovieItem>> = liveData
    fun observeErrorText(): LiveData<Boolean> = errorText
    fun observeProgressBar(): LiveData<Boolean> = progressBar

    fun fetchFavourites() {
        viewModelScope.launch {
            progressBar.value = true
            val result = withContext(Dispatchers.IO) {
                repository.getAllFavorite()
            }
            if (!result.isNullOrEmpty()) {
                liveData.value = result.toMovieItem()
            } else {
                errorText.value = true
            }
            progressBar.value = false
        }
    }

    private fun List<Movie>.toMovieItem(): List<MovieItem>? = this.map { MovieItem(it) }
}
