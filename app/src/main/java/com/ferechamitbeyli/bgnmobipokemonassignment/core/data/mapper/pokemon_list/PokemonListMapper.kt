package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base.RemoteModelMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonList
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import javax.inject.Inject

class PokemonListMapper @Inject constructor(
    private val pokemonListItemMapper: PokemonListItemMapper
) :
    RemoteModelMapper<PokemonListDto, PokemonList> {
    override fun mapFromModel(model: PokemonListDto): PokemonList {
        return PokemonList(
            count = model.count,
            next = model.next,
            previous = model.previous,
            results = model.results?.let { pokemonListItemMapper.mapModelList(it) }
        )
    }

    override fun mapToModel(type: PokemonList): PokemonListDto {
        return PokemonListDto(
            count = type.count,
            next = type.next,
            previous = type.previous,
            results = type.results?.let { pokemonListItemMapper.mapTypeList(it) }
        )
    }
}