package com.vp.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.model.MovieDetail
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private var menuMain: Menu? = null
    private var favorite: Boolean = false
    private lateinit var movieDetail: MovieDetail
    private lateinit var detailViewModel: DetailsViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this
        detailViewModel.fetchDetails(getMovieId())
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
        detailViewModel.details().observe(this, Observer {
            it?.let {
                movieDetail = it
            }
        })
        detailViewModel.isFavorite().observe(this, Observer {
            favorite = it
            if (menuMain != null) {
                menuMain!!.findItem(R.id.star)
                        .setIcon(if (!favorite) R.drawable.ic_star else R.drawable.ic_favorite_star)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuMain = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.star -> {
                if (!favorite) {
                    item.setIcon(R.drawable.ic_favorite_star)
                    detailViewModel.saveFavourite(movieDetail)
                } else {
                    item.setIcon(R.drawable.ic_star)
                    detailViewModel.removeFavourite(movieDetail.imdbID)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getMovieId(): String {
        return intent?.data?.getQueryParameter("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }
}
