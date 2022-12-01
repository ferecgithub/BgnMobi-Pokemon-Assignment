package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import app.cash.turbine.test
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation.FakePokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.domain.usecase.FetchPokemonListUseCase
import com.ferechamitbeyli.bgnmobipokemonassignment.utils.Helper.collectDataForTest
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private lateinit var viewModel: PokemonListViewModel
    private lateinit var pokemonRepository: FakePokemonRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        pokemonRepository = FakePokemonRepository()
        val useCase = FetchPokemonListUseCase(pokemonRepository)
        viewModel = PokemonListViewModel(useCase)
    }

    @Test
    fun `getPokemonList returns correct data`() = runTest {
        val expectedResult = listOf(
            PokemonListItem("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonListItem("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
        )

        viewModel.pokemonList.test {
            assertThat(awaitItem()).isInstanceOf(State.Idle::class.java)
            val successState = awaitItem()
            assertThat(successState).isInstanceOf(State.Success::class.java)
            assertThat(successState.data?.collectDataForTest()).isEqualTo(expectedResult)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}