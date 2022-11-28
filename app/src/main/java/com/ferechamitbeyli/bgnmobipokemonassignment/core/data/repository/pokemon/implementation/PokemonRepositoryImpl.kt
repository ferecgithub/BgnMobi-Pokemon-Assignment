package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.abstraction.ExecutionThread
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_detail.PokemonDetailMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonList
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonListMapper: PokemonListMapper,
    private val pokemonDetailMapper: PokemonDetailMapper,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val executionThread: ExecutionThread
) : PokemonRepository {
    override suspend fun getPokemonList(): Flow<Resource<PokemonList>> =
        safeApiCall<PokemonListDto, PokemonList>(
            dispatcher = executionThread.io,
            mapFromModel = pokemonListMapper::mapFromModel
        ) {
            pokemonRemoteDataSource.getPokemonList()
        }

    override suspend fun getPokemonById(id: Int): Flow<Resource<PokemonDetail>> =
        safeApiCall<PokemonDetailDto, PokemonDetail>(
            dispatcher = executionThread.io,
            mapFromModel = pokemonDetailMapper::mapFromModel
        ) {
            pokemonRemoteDataSource.getPokemonById(id = id)
        }

    override suspend fun getPokemonByName(name: String): Flow<Resource<PokemonDetail>> =
        safeApiCall<PokemonDetailDto, PokemonDetail>(
            dispatcher = executionThread.io,
            mapFromModel = pokemonDetailMapper::mapFromModel
        ) {
            pokemonRemoteDataSource.getPokemonByName(name = name)
        }
}