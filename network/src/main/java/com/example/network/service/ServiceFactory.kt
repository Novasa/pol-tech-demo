package com.example.network.service

interface ServiceFactory {

    fun <TService : Any> createService(config: ServiceConfig<TService>): TService
}
