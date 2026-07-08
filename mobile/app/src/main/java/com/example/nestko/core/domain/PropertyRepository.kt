package com.example.nestko.core.domain

import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun getProperties(): Flow<List<Property>>
    fun getPropertyById(id: String): Flow<Property?>
    fun getFeaturedProperties(): Flow<List<Property>>
    fun searchProperties(query: String): Flow<List<Property>>
}
