package com.example.movies.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.ui.MovieAdapter
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieBinding
import com.example.movies.ui.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModel()
    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding
    private var movieAdapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initialRecyclerview()
        }
    }

    private fun initialRecyclerview() {
        movieAdapter = MovieAdapter()

        movieAdapter?.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData.id.toString())
            startActivity(intent)
        }

        movieViewModel.isLoadingLoadmore.observe(viewLifecycleOwner, { isLoading ->
            binding?.progressBarMore?.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        movieViewModel.movies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE

                    is Resource.Success -> {
                        binding?.tvEmpty?.visibility = View.GONE
                        binding?.rvMovies?.visibility = View.VISIBLE
                        binding?.progressBar?.visibility = View.GONE
                        movies.data?.let {
                            movieAdapter?.setData(it)
                            movieViewModel.setToStore(it)
                        }

                    }

                    is Resource.Empty -> setNetworkErrorOrEmpty(movies)

                    is Resource.Error -> setNetworkErrorOrEmpty(movies)
                }
            }
        })

        val gridLayoutmanager = GridLayoutManager(context, 2)

        binding?.rvMovies?.apply {
            layoutManager = gridLayoutmanager
            setHasFixedSize(true)
            adapter = movieAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val countItem = gridLayoutmanager.itemCount
                    val listItem = gridLayoutmanager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == listItem

                    if (isLastPosition) {
                        loadMoreData()
                    }
                }
            })
        }
    }

    private fun loadMoreData() {
        movieViewModel.loadMoreData()
        movieViewModel.listMoreMovie.observe(viewLifecycleOwner, { data ->
            if (data.isNotEmpty()) {
                movieAdapter?.setData(data)
                movieViewModel.setToStore(data)
            }
        })
    }

    private fun setNetworkErrorOrEmpty(response: Resource<List<Movie>>) {
        binding?.progressBar?.visibility = View.GONE
        binding?.tvEmpty?.visibility = View.VISIBLE
        binding?.tvEmpty?.text = response.message ?: getString(R.string.network_error)
        binding?.rvMovies?.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}