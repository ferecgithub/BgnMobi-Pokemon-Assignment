package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.implementation

import android.util.Log
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.pokemon.PokemonService
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 28.11.2022
 *
 * [PokemonRemoteDataSource] implementation that performs pokemon information retrieval from API.
 */
class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonService: PokemonService
) : PokemonRemoteDataSource {
    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): Response<PokemonListDto> {
        return pokemonService.getPokemonList()
    }

    override suspend fun getPokemonById(id: Int): Response<PokemonDetailDto> =
        pokemonService.getPokemonById(id = id)

    override suspend fun getPokemonByName(name: String): Response<PokemonDetailDto> =
        pokemonService.getPokemonByName(name = name)
}