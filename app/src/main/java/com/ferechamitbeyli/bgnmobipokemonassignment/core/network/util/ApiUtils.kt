package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util

import android.util.Log
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.UiText
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Utility function to fetch and return the response that PokeAPI sends.
 *
 * @param ResponseType represents the response type.
 * @param MappedResponseType represents the [ResponseType] that will be mapped out to this type via [mapFromModel] function.
 * @param dispatcher is [CoroutineDispatcher] which will execute the [apiCall] in.
 * @param apiCall is service function that will fetch the data from PokeAPI.
 */
suspend fun <ResponseType, MappedResponseType> safeApiCall(
    mapFromModel: ((ResponseType) -> MappedResponseType)? = null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<ResponseType>
): Flow<Resource<MappedResponseType>> {
    return flow {
        try {
            emit(Resource.Loading())
            //fetch the response from the api within the given coroutine context.
            val response = withContext(dispatcher) { apiCall() }
            if (response.isSuccessful) {
                /* if the response is success
                 * which means response.code is in between 200-300
                 * we are checking if the data is null or not
                 * if not null, we are mapping it out and return
                 * if null, we are return error with the corresponding message.
                 */
                response.body()?.let { model ->
                    emit(Resource.Success(mapFromModel?.invoke(model)))
                } ?: emit(
                    Resource.Error(
                    code = response.code(),
                    text = UiText.StringResource(R.string.safeApiCall_noResult)
                ))
            } else {
                /* if response is not success
                 * we are deserializing error using
                 * parseError extension function
                 * then return it
                 */
                val message = response.errorBody().toString()
                val code = response.code()
                emit(Resource.Error(code = code, text = UiText.DynamicString(message)))
            }
        } catch (exception: Exception) {
            //handling exceptions
            when (exception) {
                is TimeoutCancellationException -> {
                    emit(Resource.Error(text = UiText.StringResource(R.string.safeApiCall_timeoutError)))
                }
                is IOException -> {
                    Log.d("ApiUtils_Exception", "msg: ${exception.message}")
                    Log.d("ApiUtils_Exception", "localmsg: ${exception.localizedMessage}")
                    Log.d("ApiUtils_Exception", "stacktrace: ${exception.printStackTrace()}")
                    emit(
                        Resource.Error(
                        text = exception.localizedMessage?.let { message ->
                            UiText.DynamicString(message)
                        } ?: UiText.StringResource(R.string.safeApiCall_checkYourConnection)
                    ))
                }
                is HttpException -> {
                    val message = exception.response()?.errorBody().toString()
                    val code = exception.response()?.code()

                    emit(Resource.Error(code = code, text = UiText.DynamicString(message)))
                }
                else -> {
                    Log.d("ApiUtils_Excp", "problem: ${exception.printStackTrace()}")
                    Log.d("ApiUtils_Excp", "problemname: ${exception.javaClass.simpleName}")
                    emit(Resource.Error(text = UiText.StringResource(R.string.safeApiCall_unknownError)))
                }
            }
        }
    }
}

//fun ResponseBody.parseError(): ErrorResponse? {
//    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//    val type: ParameterizedType = Types.newParameterizedType(
//        ErrorResponse::class.java,
//        String::class.java
//    )
//    val adapter: JsonAdapter<ErrorResponse?> = moshi.adapter(type)
//    return try {
//        adapter.fromJson(string())
//    } catch (exception: Exception) {
//        null
//    }
//}