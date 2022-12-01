package com.ferechamitbeyli.bgnmobipokemonassignment.core.paging.pokemon_list

import androidx.paging.PagingSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list.PokemonListItemMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_LIMIT
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class PokemonPagingSourceTest {

    @Test
    fun `check that paging source with offset 0 and key 0 returns error when error occurs`() = runBlocking {
        val remoteDataSource = mockk<PokemonRemoteDataSource>()
        coEvery { remoteDataSource.getPokemonList(POKEMON_LIST_LIMIT, 0) } throws IOException()
        val pagingSource = PokemonPagingSource(remoteDataSource, PokemonListItemMapper())
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = POKEMON_LIST_LIMIT,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }

    @Test
    fun `check that paging source with offset 20 and key 20 returns error when error occurs`() = runBlocking {
        val remoteDataSource = mockk<PokemonRemoteDataSource>()
        coEvery { remoteDataSource.getPokemonList(POKEMON_LIST_LIMIT, 20) } throws IOException()
        val pagingSource = PokemonPagingSource(remoteDataSource, PokemonListItemMapper())
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 20,
                loadSize = POKEMON_LIST_LIMIT,
                placeholdersEnabled = false
            )
        )
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }
}