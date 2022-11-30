package com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util

interface OnItemClickListener {
    fun <T>onItemClick(position: Int, model: T)
}