package com.example.network.repository

interface Repository<TInput, TOutput> {
    suspend fun get(input: TInput): TOutput
}
