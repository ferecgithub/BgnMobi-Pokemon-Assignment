package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.pokemon_list

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base.RemoteModelMapper
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.Result
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.ResultDto
import javax.inject.Inject

class ResultMapper @Inject constructor() :
    RemoteModelMapper<ResultDto, Result> {
    override fun mapFromModel(model: ResultDto): Result {
        return Result(
            name = model.name,
            url = model.url
        )
    }

    override fun mapToModel(type: Result): ResultDto {
        return ResultDto(
            name = type.name,
            url = type.url
        )
    }

}