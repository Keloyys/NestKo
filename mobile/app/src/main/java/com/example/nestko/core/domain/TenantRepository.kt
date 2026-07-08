package com.example.nestko.core.domain

import kotlinx.coroutines.flow.Flow

interface TenantRepository {
    fun getActiveLease(tenantId: String): Flow<Lease?>
    fun getRecentPayments(tenantId: String): Flow<List<RentPayment>>
    fun getUpcomingPayment(tenantId: String): Flow<RentPayment?>
    fun getMaintenanceRequests(tenantId: String): Flow<List<MaintenanceRequest>>
    fun getNotices(): Flow<List<Notice>>
    suspend fun submitMaintenanceRequest(request: MaintenanceRequest)
}
