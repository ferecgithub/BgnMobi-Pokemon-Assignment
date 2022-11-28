package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list

data class PokemonListDto(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultDto>
)