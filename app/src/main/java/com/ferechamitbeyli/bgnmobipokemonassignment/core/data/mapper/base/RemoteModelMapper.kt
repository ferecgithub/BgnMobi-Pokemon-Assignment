package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.mapper.base

/**
 * Base class that maps out Remote models into Domain objects
 *
 * [M] represents Remote models
 * [D] represents Domain models
 */
interface RemoteModelMapper<M, D> {

    fun mapFromModel(model: M): D

    fun mapToModel(type: D): M

    fun mapModelList(models: List<M>): List<D> {
        return models.mapTo(mutableListOf(), ::mapFromModel)
    }

    fun mapTypeList(types: List<D>): List<M> {
        return types.mapTo(mutableListOf(), ::mapToModel)
    }
}