package com.example.movies.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.Resource
import com.example.core.ui.MovieAdapter
import com.example.movies.databinding.FragmentMovieBinding
import com.example.movies.ui.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModel()
    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

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
            val movieAdapter = MovieAdapter()

            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData.id.toString())
                startActivity(intent)
            }

            movieViewModel.movies.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE

                        is Resource.Success -> {
                            binding?.tvEmpty?.visibility = View.GONE
                            binding?.rvMovies?.visibility = View.VISIBLE
                            binding?.progressBar?.visibility = View.GONE
                            movies.data?.let { movieAdapter.setData(it) }
                        }

                        is Resource.Empty -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.tvEmpty?.visibility = View.VISIBLE
                            binding?.rvMovies?.visibility = View.GONE
                        }

                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.tvEmpty?.visibility = View.VISIBLE
                            binding?.tvEmpty?.text = movies.message ?: "Terjadi kesalahan"
                            binding?.rvMovies?.visibility = View.GONE
                        }
                    }
                }
            })

            binding?.rvMovies?.layoutManager = GridLayoutManager(context, 2)
            binding?.rvMovies?.setHasFixedSize(true)
            binding?.rvMovies?.adapter = movieAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}