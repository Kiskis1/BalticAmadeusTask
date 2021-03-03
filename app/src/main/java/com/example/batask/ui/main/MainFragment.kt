package com.example.batask.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.batask.R
import com.example.batask.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = postsAdapter
        binding.refreshLayout.setOnRefreshListener(this)
        viewModel.getPosts().observe(viewLifecycleOwner) { posts ->
            postsAdapter.swapData(posts)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(R.string.title_error)
                builder.setMessage(R.string.error_has_occurred)
                builder.setPositiveButton(R.string.action_retry) { _, _ ->
                    viewModel.getAllPosts()
                }

                builder.setNegativeButton(R.string.action_cancel) { dialog, _ ->
                    dialog.cancel()
                }
                builder.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh() {
        viewModel.getAllPosts()
        binding.refreshLayout.isRefreshing = false
    }
}