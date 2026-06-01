package com.example.nestko.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.nestko.feature.guest.GuestDiscoveryScreen
import com.example.nestko.feature.guest.PropertyDetailScreen
import com.example.nestko.feature.guest.ScheduleViewingScreen
import com.example.nestko.feature.guest.ApplicationWizardScreen

import com.example.nestko.feature.tenant.TenantDashboardScreen
import com.example.nestko.feature.tenant.RentPaymentScreen
import com.example.nestko.feature.tenant.MaintenanceScreen
import com.example.nestko.feature.tenant.SubmitMaintenanceScreen
import com.example.nestko.feature.tenant.LeaseDocumentsScreen

import com.example.nestko.feature.landlord.LandlordDashboardScreen
import com.example.nestko.feature.landlord.PropertyManagementScreen
import com.example.nestko.feature.landlord.FinancialReportsScreen

import com.example.nestko.feature.auth.OnboardingScreen
import com.example.nestko.feature.auth.RoleSelectorScreen
import com.example.nestko.feature.shared.NotificationsScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        // Auth
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinished = {
                navController.navigate(Screen.RoleSelector.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Screen.RoleSelector.route) {
            RoleSelectorScreen(
                onGuestSelected = { navController.navigate(Screen.GuestDiscovery.route) },
                onTenantSelected = { navController.navigate(Screen.TenantDashboard.route) },
                onLandlordSelected = { navController.navigate(Screen.LandlordDashboard.route) }
            )
        }
        composable(Screen.Login.route) { PlaceholderScreen("Login") }
        composable(Screen.Register.route) { PlaceholderScreen("Register") }

        // Tenant
        composable(Screen.TenantDashboard.route) {
            TenantDashboardScreen(
                onPayRent = { navController.navigate(Screen.RentPayment.route) },
                onMaintenance = { navController.navigate(Screen.Maintenance.route) },
                onDocuments = { navController.navigate(Screen.Documents.route) },
                onChat = { navController.navigate(Screen.TenantChat.route) },
                onProfile = { /* TODO */ },
                onNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }
        composable(Screen.RentPayment.route) {
            RentPaymentScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Maintenance.route) {
            MaintenanceScreen(
                onBack = { navController.popBackStack() },
                onSubmitRequest = { navController.navigate(Screen.SubmitMaintenance.route) }
            )
        }
        composable(Screen.SubmitMaintenance.route) {
            SubmitMaintenanceScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Documents.route) {
            LeaseDocumentsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.TenantChat.route) { PlaceholderScreen("Tenant Chat") }

        // Guest
        composable(Screen.GuestDiscovery.route) {
            GuestDiscoveryScreen(
                onPropertyClick = { propertyId ->
                    navController.navigate(Screen.PropertyDetail.createRoute(propertyId))
                },
                onNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }
        composable(Screen.PropertyDetail.route) {
            PropertyDetailScreen(
                onBack = { navController.popBackStack() },
                onScheduleViewing = { propertyId ->
                    navController.navigate(Screen.ScheduleViewing.createRoute(propertyId))
                },
                onApplyNow = { propertyId ->
                    navController.navigate(Screen.Application.createRoute(propertyId))
                }
            )
        }
        composable(Screen.ScheduleViewing.route) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            ScheduleViewingScreen(
                propertyId = propertyId,
                onBack = { navController.popBackStack() },
                onConfirm = { navController.popBackStack() }
            )
        }
        composable(Screen.Map.route) { PlaceholderScreen("Map") }
        composable(Screen.Saved.route) { PlaceholderScreen("Saved") }
        composable(Screen.Application.route) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            ApplicationWizardScreen(
                propertyId = propertyId,
                onBack = { navController.popBackStack() },
                onComplete = { navController.popBackStack() }
            )
        }
        composable(Screen.GuestProfile.route) { PlaceholderScreen("Guest Profile") }

        // Landlord
        composable(Screen.LandlordDashboard.route) {
            LandlordDashboardScreen(
                onProperties = { navController.navigate(Screen.PropertyManagement.route) },
                onTenants = { navController.navigate(Screen.TenantManagement.route) },
                onFinances = { navController.navigate(Screen.Finances.route) },
                onMaintenance = { navController.navigate(Screen.Maintenance.route) },
                onNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }
        composable(Screen.PropertyManagement.route) {
            PropertyManagementScreen(
                onBack = { navController.popBackStack() },
                onAddProperty = { /* TODO */ },
                onPropertyClick = { /* TODO */ }
            )
        }
        composable(Screen.TenantManagement.route) { PlaceholderScreen("Tenant Management") }
        composable(Screen.Finances.route) {
            FinancialReportsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.LandlordChat.route) { PlaceholderScreen("Landlord Chat") }

        // Shared
        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = "Welcome to $name", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
    }
}
