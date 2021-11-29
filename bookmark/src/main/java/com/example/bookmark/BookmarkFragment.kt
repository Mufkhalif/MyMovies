package com.example.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookmark.databinding.FragmentBookmarkBinding
import com.example.core.ui.MovieAdapter
import com.example.movies.ui.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class BookmarkFragment : Fragment() {
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private var _fragmentBookmark: FragmentBookmarkBinding? = null
    private val binding get() = _fragmentBookmark

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBookmark = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(bookmarkModule)

        if (activity != null) {
            val movieAdapter = MovieAdapter()

            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, selectedData.id.toString())
                startActivity(intent)
            }

            bookmarkViewModel.movies.observe(viewLifecycleOwner, { movies ->
                movieAdapter.setData(movies)

                if (movies.isEmpty()) {
                    binding?.tvEmpty?.visibility = View.VISIBLE
                    binding?.rvMovies?.visibility = View.GONE
                } else {
                    binding?.tvEmpty?.visibility = View.GONE
                    binding?.rvMovies?.visibility = View.VISIBLE
                }

            })

            binding?.rvMovies?.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentBookmark = null
    }

}