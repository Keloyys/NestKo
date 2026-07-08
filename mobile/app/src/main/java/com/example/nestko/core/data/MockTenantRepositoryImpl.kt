package com.example.nestko.core.data

import com.example.nestko.core.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockTenantRepositoryImpl @Inject constructor() : TenantRepository {

    private val mockLease = Lease(
        id = "lease1",
        unitId = "u1",
        tenantId = "t1",
        landlordId = "l1",
        startDate = LocalDateTime.now().minusMonths(6),
        endDate = LocalDateTime.now().plusMonths(6),
        monthlyRent = 25000.0,
        depositAmount = 50000.0
    )

    private val mockPayments = listOf(
        RentPayment("p1", "lease1", "t1", 25000.0, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5), "Bank Transfer", PaymentStatus.PAID),
        RentPayment("p2", "lease1", "t1", 25000.0, LocalDateTime.now().minusMonths(1).minusDays(5), LocalDateTime.now().minusMonths(1).minusDays(5), "Bank Transfer", PaymentStatus.PAID),
        RentPayment("p3", "lease1", "t1", 25000.0, LocalDateTime.now().minusMonths(2).minusDays(5), LocalDateTime.now().minusMonths(2).minusDays(5), "GCash", PaymentStatus.PAID)
    )

    private val mockUpcomingPayment = RentPayment(
        id = "p4",
        leaseId = "lease1",
        tenantId = "t1",
        amount = 25000.0,
        dueDate = LocalDateTime.now().plusDays(25),
        status = PaymentStatus.PENDING
    )

    private val mockMaintenanceRequests = mutableListOf(
        MaintenanceRequest("m1", "u1", "t1", "Plumbing", "Leaky faucet in the kitchen", Priority.MEDIUM, emptyList(), MaintenanceStatus.IN_PROGRESS, LocalDateTime.now().minusDays(2)),
        MaintenanceRequest("m2", "u1", "t1", "Electrical", "Light bulb replacement in living room", Priority.LOW, emptyList(), MaintenanceStatus.RESOLVED, LocalDateTime.now().minusDays(10))
    )

    private val mockNotices = listOf(
        Notice("n1", "Policy Update", "New Garbage Collection Schedule", "Starting next week, garbage will be collected every Monday and Thursday at 8:00 AM.", LocalDateTime.now().minusDays(1)),
        Notice("n2", "Inspection", "Annual Fire Safety Inspection", "The annual fire safety inspection for all units will take place on June 15th from 9 AM to 5 PM.", LocalDateTime.now().minusDays(3))
    )

    override fun getActiveLease(tenantId: String): Flow<Lease?> = flowOf(mockLease)

    override fun getRecentPayments(tenantId: String): Flow<List<RentPayment>> = flowOf(mockPayments)

    override fun getUpcomingPayment(tenantId: String): Flow<RentPayment?> = flowOf(mockUpcomingPayment)

    override fun getMaintenanceRequests(tenantId: String): Flow<List<MaintenanceRequest>> = flowOf(mockMaintenanceRequests)

    override fun getNotices(): Flow<List<Notice>> = flowOf(mockNotices)

    override suspend fun submitMaintenanceRequest(request: MaintenanceRequest) {
        mockMaintenanceRequests.add(0, request)
    }
}
