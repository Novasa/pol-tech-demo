package com.example.network.repository

import kotlinx.coroutines.flow.Flow

interface FlowRepository<TInput, TOutput> {
    fun flow(input: TInput): Flow<TOutput>
}
