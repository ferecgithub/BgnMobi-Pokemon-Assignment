package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.LayoutPaginationErrorBinding

/**
 * Created by Ferec Hamitbeyli on 30.11.2022.
 */

class PokemonListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonListLoadStateAdapter.PokemonListLoadStateViewHolder>() {

    inner class PokemonListLoadStateViewHolder(
        private val binding: LayoutPaginationErrorBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonLayoutPaginationErrorTryAgain.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.progressBarLayoutPaginationError.isVisible = loadState is LoadState.Loading
            binding.buttonLayoutPaginationErrorTryAgain.isVisible = loadState !is LoadState.Loading
            binding.imageViewLayoutPaginationErrorWarning.isVisible =
                loadState !is LoadState.Loading
            binding.textViewLayoutPaginationErrorWarning.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: PokemonListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokemonListLoadStateViewHolder =
        PokemonListLoadStateViewHolder(
            LayoutPaginationErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            retry
        )
}