package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base.RemoteModelMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListItemDto
import javax.inject.Inject

class PokemonListItemMapper @Inject constructor() :
    RemoteModelMapper<PokemonListItemDto, PokemonListItem> {
    override fun mapFromModel(model: PokemonListItemDto): PokemonListItem {
        return PokemonListItem(
            name = model.name,
            url = model.url
        )
    }

    override fun mapToModel(type: PokemonListItem): PokemonListItemDto {
        return PokemonListItemDto(
            name = type.name,
            url = type.url
        )
    }

}