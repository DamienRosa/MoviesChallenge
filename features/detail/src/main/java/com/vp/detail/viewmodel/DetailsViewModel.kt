package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgr.data.db.model.Movie
import com.dgr.data.db.repository.MovieRepository
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(
        private val detailService: DetailService,
        private val repository: MovieRepository) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun isFavorite(): LiveData<Boolean> = isFavorite

    fun fetchDetails(movieId: String) {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(movieId).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                details.postValue(response?.body())

                response?.body()?.title?.let {
                    title.postValue(it)
                }

                response?.body()?.let {
                    fetchFavorite(it.imdbID)
                }
                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })
    }

    fun fetchFavorite(imdbID: String) {
        viewModelScope.launch {
            val movie = withContext(Dispatchers.IO) {
                repository.getFavorite(imdbID)
            }
            isFavorite.value = movie != null
        }
    }

    fun saveFavourite(movie: MovieDetail) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.addMovie(movie.toEntity())
            }
        }
    }

    fun removeFavourite(imdbID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removeMovie(imdbID)
            }
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }

    private fun MovieDetail.toEntity(): Movie =
            Movie(
                    title = this.title,
                    year = this.year,
                    runtime = this.runtime,
                    director = this.director,
                    plot = this.plot,
                    poster = this.poster,
                    imdbID = this.imdbID
            )
}
