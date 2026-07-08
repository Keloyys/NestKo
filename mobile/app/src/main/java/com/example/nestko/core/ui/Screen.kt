package com.example.nestko.core.ui

sealed class Screen(val route: String) {
    // Auth
    object Onboarding : Screen("onboarding")
    object RoleSelector : Screen("role_selector")
    object Login : Screen("login")
    object Register : Screen("register")

    // Tenant
    object TenantDashboard : Screen("tenant_dashboard")
    object RentPayment : Screen("rent_payment")
    object Maintenance : Screen("maintenance")
    object SubmitMaintenance : Screen("submit_maintenance")
    object Documents : Screen("documents")
    object TenantChat : Screen("tenant_chat")

    // Guest
    object GuestDiscovery : Screen("guest_discovery")
    object PropertyDetail : Screen("property_detail/{propertyId}") {
        fun createRoute(propertyId: String) = "property_detail/$propertyId"
    }
    object ScheduleViewing : Screen("schedule_viewing/{propertyId}") {
        fun createRoute(propertyId: String) = "schedule_viewing/$propertyId"
    }
    object Map : Screen("map")
    object Saved : Screen("saved")
    object Application : Screen("application/{propertyId}") {
        fun createRoute(propertyId: String) = "application/$propertyId"
    }
    object GuestProfile : Screen("guest_profile")

    // Landlord
    object LandlordDashboard : Screen("landlord_dashboard")
    object PropertyManagement : Screen("property_management")
    object TenantManagement : Screen("tenant_management")
    object Finances : Screen("finances")
    object LandlordChat : Screen("landlord_chat")

    // Shared
    object Notifications : Screen("notifications")
}
