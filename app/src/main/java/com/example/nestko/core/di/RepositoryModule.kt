package com.example.nestko.core.di

import com.example.nestko.core.data.MockLandlordRepositoryImpl
import com.example.nestko.core.data.MockPropertyRepositoryImpl
import com.example.nestko.core.data.MockTenantRepositoryImpl
import com.example.nestko.core.domain.LandlordRepository
import com.example.nestko.core.domain.PropertyRepository
import com.example.nestko.core.domain.TenantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPropertyRepository(
        mockPropertyRepositoryImpl: MockPropertyRepositoryImpl
    ): PropertyRepository

    @Binds
    @Singleton
    abstract fun bindTenantRepository(
        mockTenantRepositoryImpl: MockTenantRepositoryImpl
    ): TenantRepository

    @Binds
    @Singleton
    abstract fun bindLandlordRepository(
        mockLandlordRepositoryImpl: MockLandlordRepositoryImpl
    ): LandlordRepository
}
