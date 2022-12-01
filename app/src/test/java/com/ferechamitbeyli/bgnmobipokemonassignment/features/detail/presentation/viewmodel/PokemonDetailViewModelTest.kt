package com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.UiText
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.Sprites
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation.FakePokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.domain.usecase.FetchPokemonByNameUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var pokemonRepository: FakePokemonRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        pokemonRepository = FakePokemonRepository()
        val useCase = FetchPokemonByNameUseCase(pokemonRepository)
        viewModel = PokemonDetailViewModel(useCase)
    }

    @Test
    fun `fetchPokemonByName returns correct data when given correct pokemon name`() = runTest {
        val name = "bulbasaur"

        val expectedResult = PokemonDetail(
            7,
            1,
            name,
            69,
            Sprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"
            )
        )

        viewModel.pokemon.test {
            viewModel.fetchPokemonByName(name)
            assertThat(awaitItem()).isInstanceOf(State.Idle::class.java)
            val successState = awaitItem()
            assertThat(successState).isInstanceOf(State.Success::class.java)
            assertThat(successState.data).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `fetchPokemonByName returns error data when given wrong pokemon name`() = runTest {
        val name = "ivysaur"

        val expectedResult = PokemonDetail(
            7,
            1,
            name,
            69,
            Sprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"
            )
        )

        viewModel.pokemon.test {
            pokemonRepository.setErrorState(true)
            viewModel.fetchPokemonByName(name)
            assertThat(awaitItem()).isInstanceOf(State.Idle::class.java)
            val errorState = awaitItem()
            assertThat(errorState).isInstanceOf(State.Error::class.java)
            assertThat(errorState.error).isInstanceOf(UiText.DynamicString::class.java)
            assertThat((errorState.error as UiText.DynamicString).value).isEqualTo("Pokemon Error")
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}