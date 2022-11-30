package com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util

interface OnItemClickListener<T> {
    fun onItemClick(position: Int, model: T)
}