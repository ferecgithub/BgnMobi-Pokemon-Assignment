package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_detail

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base.RemoteModelMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.Sprites
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.SpritesDto
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 30.11.2022.
 */

class SpritesMapper @Inject constructor() :
    RemoteModelMapper<SpritesDto, Sprites> {
    override fun mapFromModel(model: SpritesDto): Sprites {
        return Sprites(
            front_default = model.front_default,
            back_default = model.back_default
        )
    }

    override fun mapToModel(type: Sprites): SpritesDto {
        return SpritesDto(
            front_default = type.front_default,
            back_default = type.back_default
        )
    }

}