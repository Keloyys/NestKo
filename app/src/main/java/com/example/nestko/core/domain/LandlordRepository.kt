package com.example.nestko.core.domain

import kotlinx.coroutines.flow.Flow

interface LandlordRepository {
    fun getPortfolioSummary(landlordId: String): Flow<PortfolioSummary>
    fun getOwnedProperties(landlordId: String): Flow<List<Property>>
    fun getActiveLeases(landlordId: String): Flow<List<Lease>>
    fun getMaintenanceRequests(landlordId: String): Flow<List<MaintenanceRequest>>
    fun getFinancialTransactions(landlordId: String): Flow<List<FinancialTransaction>>
    fun getMonthlyRevenueData(landlordId: String): Flow<List<Pair<String, Double>>>
}
