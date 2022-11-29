package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.LayoutPokemonListItemBinding

/**
 * Created by Ferec Hamitbeyli on 30.11.2022.
 */

class PokemonListAdapter : PagingDataAdapter<PokemonListItem, RecyclerView.ViewHolder>(differ) {

    companion object {
        private val differ = object : DiffUtil.ItemCallback<PokemonListItem>() {
            override fun areItemsTheSame(
                oldItem: PokemonListItem,
                newItem: PokemonListItem
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: PokemonListItem,
                newItem: PokemonListItem
            ): Boolean = oldItem == newItem

        }
    }

    inner class PokemonListViewHolder(private val binding: LayoutPokemonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonListItem?) {
            binding.textViewLayoutPokemonListItemName.text = item?.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemonItem = getItem(position)
        (holder as PokemonListViewHolder).bind(pokemonItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PokemonListViewHolder(
            LayoutPokemonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}