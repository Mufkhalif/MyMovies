package com.example.movies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movies.R
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.movies.databinding.ActivityDetailMovieBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val detailMovieViewModel: DetailMovieViewModel by viewModel()
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkModuleBookmark()

        val extras = intent.extras

        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE)
            if (movieId != null) {
                detailMovieViewModel.setSelectedMovie(movieId)
                detailMovieViewModel.movie.observe(this, { movie ->
                    when (movie) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.mainContent.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.mainContent.visibility = View.VISIBLE
                            movie.data?.let { setRenderContent(it) }

                        }

                        is Resource.Empty -> {
                            binding.progressBar.visibility = View.GONE
                            binding.mainContent.visibility = View.GONE
                            Log.d("Detail", movie.message ?: "Terjadi kesalahan")
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.mainContent.visibility = View.GONE
                            Log.d("Detail", movie.message ?: "Terjadi kesalahan")
                        }
                    }
                })

                detailMovieViewModel.movieBookmarked.observe(this, { movies ->
                    if (movies.isNotEmpty()) {
                        binding.btnBookmark.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_favorite
                            )
                        )
                        binding.btnBookmark.setOnClickListener {
                            detailMovieViewModel.deleteBookmark()
                            Toast.makeText(
                                applicationContext,
                                R.string.remove_bookmark,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        binding.btnBookmark.setImageDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_favorite_default
                            )
                        )
                        binding.btnBookmark.setOnClickListener {
                            detailMovieViewModel.addBookmark()
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.add_bookmark),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    private fun setRenderContent(movie: Movie) {
        with(binding) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            tvVote.text = movie.voteAverage.toString()
        }

        Glide.with(this)
            .load("https://www.themoviedb.org/t/p/original${movie.posterPath}")
            .transform(RoundedCorners(20))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding.imgPoster)
    }

    private fun checkModuleBookmark() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleBookmark = "bookmark"

        binding.btnBookmark.visibility =
            if (splitInstallManager.installedModules.contains(moduleBookmark)) View.VISIBLE else View.GONE
    }


    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

}