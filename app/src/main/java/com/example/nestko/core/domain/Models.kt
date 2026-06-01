package com.example.nestko.core.domain

import java.time.LocalDateTime

enum class UserRole {
    TENANT, GUEST, LANDLORD
}

data class User(
    val id: String,
    val role: UserRole,
    val name: String,
    val email: String,
    val phone: String,
    val profilePhotoUrl: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class Address(
    val street: String,
    val barangay: String,
    val city: String,
    val province: String,
    val zipCode: String,
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    fun fullAddress(): String = "$street, $barangay, $city, $province $zipCode"
}

enum class PropertyType {
    APARTMENT, HOUSE, CONDO, COMMERCIAL
}

data class Property(
    val id: String,
    val landlordId: String,
    val address: Address,
    val type: PropertyType,
    val units: List<Unit>,
    val amenities: List<String>,
    val photos: List<String>,
    val description: String,
    val isListed: Boolean = true,
    val rating: Float = 0f,
    val reviewCount: Int = 0
)

data class Unit(
    val id: String,
    val propertyId: String,
    val unitNumber: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val floorAreaSqm: Double,
    val monthlyRent: Double,
    val isOccupied: Boolean = false,
    val currentTenantId: String? = null
)

enum class ApplicationStatus {
    PENDING, APPROVED, REJECTED, WITHDRAWN
}

data class RentalApplication(
    val id: String,
    val guestId: String,
    val unitId: String,
    val status: ApplicationStatus = ApplicationStatus.PENDING,
    val submittedAt: LocalDateTime = LocalDateTime.now()
)

data class Lease(
    val id: String,
    val unitId: String,
    val tenantId: String,
    val landlordId: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val monthlyRent: Double,
    val depositAmount: Double,
    val status: LeaseStatus = LeaseStatus.ACTIVE
)

enum class LeaseStatus {
    ACTIVE, EXPIRED, TERMINATED
}

data class RentPayment(
    val id: String,
    val leaseId: String,
    val tenantId: String,
    val amount: Double,
    val dueDate: LocalDateTime,
    val paidDate: LocalDateTime? = null,
    val method: String? = null,
    val status: PaymentStatus = PaymentStatus.PENDING
)

enum class PaymentStatus {
    PENDING, PAID, OVERDUE
}

data class MaintenanceRequest(
    val id: String,
    val unitId: String,
    val tenantId: String,
    val category: String,
    val description: String,
    val priority: Priority = Priority.MEDIUM,
    val photos: List<String> = emptyList(),
    val status: MaintenanceStatus = MaintenanceStatus.SUBMITTED,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val scheduledDate: LocalDateTime? = null
)

enum class Priority {
    LOW, MEDIUM, URGENT
}

enum class MaintenanceStatus {
    SUBMITTED, IN_PROGRESS, SCHEDULED, RESOLVED
}

data class Notice(
    val id: String,
    val type: String, // Inspection, Policy Update, Emergency
    val title: String,
    val content: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val isRead: Boolean = false
)

data class FinancialTransaction(
    val id: String,
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date: LocalDateTime = LocalDateTime.now(),
    val description: String,
    val propertyId: String? = null
)

enum class TransactionType {
    INCOME, EXPENSE
}

data class PortfolioSummary(
    val totalProperties: Int,
    val occupiedUnits: Int,
    val totalUnits: Int,
    val monthlyRevenue: Double,
    val revenueTrend: Double, // Percentage
    val pendingMaintenance: Int
)
