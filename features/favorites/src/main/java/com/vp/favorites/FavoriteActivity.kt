package com.vp.favorites

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.vp.favorites.model.MovieItem
import com.vp.favorites.viewmodel.FavoriteViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import javax.inject.Inject

class FavoriteActivity : DaggerAppCompatActivity() {

    private val lAdapter: GroupAdapter<ViewHolder> by lazy { GroupAdapter<ViewHolder>() }
    private lateinit var favoriteViewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favoriteViewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel::class.java)
        favoriteViewModel.observeMovies().observe(this, observerMovies())
        favoriteViewModel.observeErrorText().observe(this, observerError())
        favoriteViewModel.observeProgressBar().observe(this, observerProgressBar())

        setupList()
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.fetchFavourites()
    }

    private fun setupList() {
        val orientation = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        recyclerView.apply {
            layoutManager = GridLayoutManager(context!!, if (orientation) 2 else 3)
            setHasFixedSize(true)
            adapter = lAdapter
        }
    }

    private fun observerMovies(): Observer<List<MovieItem>> = Observer {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(recyclerView)
        lAdapter.update(it)
        lAdapter.setOnItemClickListener { item, _ ->
            val movie = (item as MovieItem).movie
            val detailUri = "app://movies/detail"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailUri))
            intent.data = Uri.parse("$detailUri?imdbID=${movie.imdbID}")
            startActivity(intent)
        }
    }

    private fun observerError(): Observer<in Boolean> = Observer {
        viewAnimator.displayedChild = viewAnimator.indexOfChild(errorText)
    }

    private fun observerProgressBar(): Observer<in Boolean> = Observer {
        if (it) {
            viewAnimator.displayedChild = viewAnimator.indexOfChild(progressBar)
        }
    }
}