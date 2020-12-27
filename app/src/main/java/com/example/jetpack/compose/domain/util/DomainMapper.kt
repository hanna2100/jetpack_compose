package com.example.jetpack.compose.domain.util

interface DomainMapper<T, DomainModel> {
    fun mapToDomainModel(domain: T): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): T
}