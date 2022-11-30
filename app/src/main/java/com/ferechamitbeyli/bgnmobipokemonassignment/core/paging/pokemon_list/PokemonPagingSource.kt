package com.ferechamitbeyli.bgnmobipokemonassignment.core.paging.pokemon_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListItemMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_LIMIT
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

class PokemonPagingSource constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonListItemMapper: PokemonListItemMapper
) : PagingSource<Int, PokemonListItem>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonListItem>): Int? =
        state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(POKEMON_LIST_LIMIT) ?: page?.nextKey?.minus(POKEMON_LIST_LIMIT)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListItem> {
        val currentPosition = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                pokemonRemoteDataSource.getPokemonList(
                    limit = params.loadSize,
                    offset = currentPosition
                )

            val data =
                response.body()?.results?.let { pokemonListItemMapper.mapModelList(it) } as List<PokemonListItem>

            LoadResult.Page(
                data = data,
                prevKey = if (currentPosition == STARTING_PAGE_INDEX) null else currentPosition.minus(
                    POKEMON_LIST_LIMIT
                ),
                nextKey = if (data.isEmpty()) null else currentPosition.plus(POKEMON_LIST_LIMIT)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}