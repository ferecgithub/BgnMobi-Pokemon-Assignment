package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.abstraction.ExecutionThread
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_detail.PokemonDetailMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListItemMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonList
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListItemDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_LIMIT
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.safeApiCall
import com.ferechamitbeyli.bgnmobipokemonassignment.core.paging.pokemon_list.PokemonPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonListItemMapper: PokemonListItemMapper,
    private val pokemonDetailMapper: PokemonDetailMapper,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val executionThread: ExecutionThread
) : PokemonRepository {
    override suspend fun getPokemonList(): Flow<PagingData<PokemonListItem>> =
        Pager(
            config = PagingConfig(
                pageSize = POKEMON_LIST_LIMIT,
                maxSize = POKEMON_LIST_LIMIT + (POKEMON_LIST_LIMIT + 2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(
                    pokemonRemoteDataSource,
                    pokemonListItemMapper
                )
            }
        ).flow

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