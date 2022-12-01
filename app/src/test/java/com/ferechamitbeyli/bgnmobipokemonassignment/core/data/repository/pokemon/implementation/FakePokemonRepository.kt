package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation

import androidx.paging.PagingData
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.UiText
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.Sprites
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ferec Hamitbeyli on 1.12.2022.
 */

class FakePokemonRepository : PokemonRepository {

    private var shouldReturnError = false

    private val pokemonList = listOf<PokemonListItem>(
        PokemonListItem("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
        PokemonListItem("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
    )

    fun setErrorState(isError: Boolean) {
        shouldReturnError = isError
    }

    override suspend fun getPokemonList(): Flow<PagingData<PokemonListItem>> {
        return flow<PagingData<PokemonListItem>> {
            emit(PagingData.from(pokemonList))
        }

    }

    override suspend fun getPokemonById(id: Int): Flow<Resource<PokemonDetail>> {
        val pokemon = PokemonDetail(
            7,
            id,
            "bulbasaur",
            69,
            Sprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"
            )
        )

        return flow {
            if (shouldReturnError) {
                emit(Resource.Error(text = UiText.DynamicString("Pokemon Error")))
            } else {
                emit(Resource.Success(pokemon))
            }
        }
    }

    override suspend fun getPokemonByName(name: String): Flow<Resource<PokemonDetail>> {
        val pokemon = PokemonDetail(
            7,
            1,
            name,
            69,
            Sprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"
            )
        )

        return flow {
            if (shouldReturnError) {
                emit(Resource.Error(text = UiText.DynamicString("Pokemon Error")))
            } else {
                emit(Resource.Success(pokemon))
            }
        }
    }
}