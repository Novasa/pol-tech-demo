package com.example.network.service

interface ServiceFactory {

    fun <TService> createService(serviceClass: Class<TService>, config: ServiceConfig) : TService
}