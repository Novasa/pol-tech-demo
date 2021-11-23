package com.example.network.repository

import com.example.utility.state.Data
import kotlinx.coroutines.flow.*

interface FlowDataSource<TInput : Any, TOutput : Any> {

    fun flow(input: TInput): Flow<Data<TOutput>> = flowInternal(input)
        .map<TOutput, Data<TOutput>> { Data.Success(it) }
        .onStart { emit(Data.Progress) }
        .catch { emit(Data.Failure(it)) }
        .onCompletion { emit(Data.Complete) }

    fun flowInternal(input: TInput): Flow<TOutput>
}
