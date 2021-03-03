package com.example.batask.ui.singlepost

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.batask.R
import com.example.batask.databinding.SinglePostFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SinglePostFragment : Fragment() {

    private val viewModel: SinglePostViewModel by viewModels()
    private val args: SinglePostFragmentArgs by navArgs()

    private var _binding: SinglePostFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SinglePostFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePost()

        //could be better if it wasn't duplicated in mainfragment and here
        //did you even want error dialog here?
        viewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(R.string.title_error)
                builder.setMessage(R.string.error_has_occurred)
                builder.setPositiveButton(R.string.action_retry) { _, _ ->
                    observePost()
                }

                builder.setNegativeButton(R.string.action_cancel) { dialog, _ ->
                    dialog.cancel()
                }
                builder.show()
            }
        }
    }

    //if no need for error dialog then this can be simplified with no Pair
    private fun observePost() {
        viewModel.getPostData(args.post).observe(viewLifecycleOwner) {
            it.first.observe(viewLifecycleOwner) { post ->
                binding.textPostTitle.text = post?.title
                binding.textPostBody.text = post?.body
            }
            it.second.observe(viewLifecycleOwner) { user ->
                binding.textUserName.text = user?.name
                binding.imageUser.load(getString(R.string.user_photo_url, user?.id)){
                    error(R.drawable.ic_baseline_error_24)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}