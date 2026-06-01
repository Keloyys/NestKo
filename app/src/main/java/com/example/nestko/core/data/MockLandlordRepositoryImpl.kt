package com.example.nestko.core.data

import com.example.nestko.core.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockLandlordRepositoryImpl @Inject constructor() : LandlordRepository {

    override fun getPortfolioSummary(landlordId: String): Flow<PortfolioSummary> = flowOf(
        PortfolioSummary(
            totalProperties = 4,
            occupiedUnits = 8,
            totalUnits = 12,
            monthlyRevenue = 285000.0,
            revenueTrend = 12.5,
            pendingMaintenance = 3
        )
    )

    override fun getOwnedProperties(landlordId: String): Flow<List<Property>> = flowOf(
        listOf(
            Property("1", landlordId, Address("IT Park", "Lahug", "Cebu City", "Cebu", "6000"), PropertyType.CONDO, emptyList(), emptyList(), listOf("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80&w=1000"), "Modern IT Park Condo"),
            Property("2", landlordId, Address("Escario St.", "Capitol Site", "Cebu City", "Cebu", "6000"), PropertyType.APARTMENT, emptyList(), emptyList(), listOf("https://images.unsplash.com/photo-1493809842364-78817add7ffb?auto=format&fit=crop&q=80&w=1000"), "Capitol Apartment"),
            Property("3", landlordId, Address("A.S. Fortuna", "Banilad", "Mandaue City", "Cebu", "6014"), PropertyType.HOUSE, emptyList(), emptyList(), listOf("https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&q=80&w=1000"), "Banilad Family House")
        )
    )

    override fun getActiveLeases(landlordId: String): Flow<List<Lease>> = flowOf(
        listOf(
            Lease("lease1", "u1", "t1", landlordId, LocalDateTime.now().minusMonths(3), LocalDateTime.now().plusMonths(9), 25000.0, 50000.0),
            Lease("lease2", "u2", "t2", landlordId, LocalDateTime.now().minusMonths(1), LocalDateTime.now().plusMonths(11), 35000.0, 70000.0)
        )
    )

    override fun getMaintenanceRequests(landlordId: String): Flow<List<MaintenanceRequest>> = flowOf(
        listOf(
            MaintenanceRequest("m1", "u1", "t1", "Plumbing", "Leaky faucet in kitchen", Priority.MEDIUM, emptyList(), MaintenanceStatus.SUBMITTED),
            MaintenanceRequest("m2", "u3", "t3", "Electrical", "AC not cooling", Priority.URGENT, emptyList(), MaintenanceStatus.IN_PROGRESS)
        )
    )

    override fun getFinancialTransactions(landlordId: String): Flow<List<FinancialTransaction>> = flowOf(
        listOf(
            FinancialTransaction("f1", TransactionType.INCOME, "Rent", 25000.0, LocalDateTime.now(), "Rent payment for Unit 1201"),
            FinancialTransaction("f2", TransactionType.EXPENSE, "Repairs", 1500.0, LocalDateTime.now().minusDays(2), "Kitchen faucet repair"),
            FinancialTransaction("f3", TransactionType.INCOME, "Rent", 35000.0, LocalDateTime.now().minusDays(3), "Rent payment for Unit 808")
        )
    )

    override fun getMonthlyRevenueData(landlordId: String): Flow<List<Pair<String, Double>>> = flowOf(
        listOf(
            "Jan" to 220000.0,
            "Feb" to 235000.0,
            "Mar" to 210000.0,
            "Apr" to 260000.0,
            "May" to 285000.0,
            "Jun" to 290000.0
        )
    )
}
