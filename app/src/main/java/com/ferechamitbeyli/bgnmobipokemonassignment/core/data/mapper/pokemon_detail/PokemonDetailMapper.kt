package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_detail

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base.RemoteModelMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import javax.inject.Inject

class PokemonDetailMapper @Inject constructor(
    private val spritesMapper: SpritesMapper
) :
    RemoteModelMapper<PokemonDetailDto, PokemonDetail> {
    override fun mapFromModel(model: PokemonDetailDto): PokemonDetail {
        return PokemonDetail(
            height = model.height,
            id = model.id,
            name = model.name,
            weight = model.weight,
            sprites = model.sprites?.let { spritesMapper.mapFromModel(it) }
        )
    }

    override fun mapToModel(type: PokemonDetail): PokemonDetailDto {
        return PokemonDetailDto(
            height = type.height,
            id = type.id,
            name = type.name,
            weight = type.weight,
            sprites = type.sprites?.let { spritesMapper.mapToModel(it) }
        )
    }
}