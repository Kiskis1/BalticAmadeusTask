package com.example.batask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.batask.databinding.ItemPostBinding
import com.example.batask.model.Post

class PostsAdapter :
    ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<Post>) {
        submitList(data.toMutableList())
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding,
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return
            val clicked = getItem(adapterPosition)
            val dir = MainFragmentDirections.actionMainFragmentToSinglePostFragment(clicked)
            v!!.findNavController().navigate(dir)
        }

        fun bind(item: Post) = with(itemView) {
            with(binding) {
                title.text = item.title
            }
        }
    }

    private class PostDC : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post,
        ) = oldItem == newItem
    }
}

