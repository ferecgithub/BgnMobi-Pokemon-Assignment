package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction

import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import retrofit2.Response

/**
 * Created by Ferec Hamitbeyli on 28.11.2022
 *
 * Interface representing network calls to the pokemon information
 * retrieval functions of the PokeAPI backend.
 */
interface PokemonRemoteDataSource {
    suspend fun getPokemonList(): Response<PokemonListDto>
    suspend fun getPokemonById(id: Int): Response<PokemonDetailDto>
    suspend fun getPokemonByName(name: String): Response<PokemonDetailDto>
}