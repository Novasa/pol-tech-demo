package com.example.utility.coroutines

import kotlinx.coroutines.flow.*

abstract class FlowChannel<P : Any, R : Any> {

    fun flow(params: P): Flow<Data<R>> = createFlow(params)
        .map<R, Data<R>> { Data.Success(it) }
        .onStart { emit(Data.Progress) }
        .catch { emit(Data.Failure(it)) }
        .onCompletion { emit(Data.Complete) }

    protected abstract fun createFlow(params: P): Flow<R>
}

abstract class FlowOutChannel<R : Any> : FlowChannel<Unit, R>() {
    fun flow() = flow(Unit)
}


fun <P : Any, R : Any> flowChannel(block: suspend FlowCollector<R>.(P) -> Unit) = object : FlowChannel<P, R>() {
    override fun createFlow(params: P) = flow {
        block(this, params)
    }
}

fun <R : Any> flowOutChannel(block: suspend FlowCollector<R>.() -> Unit) = object : FlowOutChannel<R>() {
    override fun createFlow(params: Unit) = flow(block)
}
