package com.ferechamitbeyli.bgnmobipokemonassignment.core.paging.pokemon_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListItemMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListItemDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.pokemon.PokemonService
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_LIMIT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class PokemonPagingSource @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonListItemMapper: PokemonListItemMapper
) :
    PagingSource<Int, PokemonListItem>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonListItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                pokemonRemoteDataSource.getPokemonList(offset = page, limit = params.loadSize)

            val data = response.body()?.results?.let { pokemonListItemMapper.mapModelList(it) } as List<PokemonListItem>
            LoadResult.Page(
                data = data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (data.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}