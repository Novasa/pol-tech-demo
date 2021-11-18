package com.example.network.repository

import kotlinx.coroutines.flow.Flow

interface FlowRepository<TData> {
    val flow: Flow<TData>
}
