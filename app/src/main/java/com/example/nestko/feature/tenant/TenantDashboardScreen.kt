package com.example.nestko.feature.tenant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nestko.core.domain.PaymentStatus
import com.example.nestko.core.ui.MaintenanceTicketCard
import com.example.nestko.core.ui.QuickActionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantDashboardScreen(
    onPayRent: () -> Unit,
    onMaintenance: () -> Unit,
    onDocuments: () -> Unit,
    onChat: () -> Unit,
    onProfile: () -> Unit,
    onNotifications: () -> Unit,
    viewModel: TenantDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("M", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Good morning,", style = MaterialTheme.typography.labelSmall)
                            Text("Maria 👋", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onNotifications) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                RentStatusCard(
                    amount = uiState.upcomingPayment?.amount ?: 0.0,
                    dueDate = uiState.upcomingPayment?.dueDate?.toLocalDate()?.toString() ?: "N/A",
                    status = uiState.upcomingPayment?.status ?: PaymentStatus.PENDING,
                    onPayNow = onPayRent
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickActionItem(Icons.Default.AccountBalanceWallet, "Pay Rent", onPayRent)
                    QuickActionItem(Icons.Default.Build, "Maintenance", onMaintenance)
                    QuickActionItem(Icons.Default.Description, "Documents", onDocuments)
                    QuickActionItem(Icons.Default.Chat, "Chat", onChat)
                }
            }

            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Recent Maintenance", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        TextButton(onClick = onMaintenance) {
                            Text("See All", color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                    
                    if (uiState.recentMaintenance.isEmpty()) {
                        Text("No recent requests", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            uiState.recentMaintenance.forEach { ticket ->
                                MaintenanceTicketCard(ticket = ticket, onClick = { /* TODO */ })
                            }
                        }
                    }
                }
            }

            item {
                Column {
                    Text(text = "Property Info", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Unit 1201, IT Park", fontWeight = FontWeight.Bold)
                                Text("Lahug, Cebu City", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RentStatusCard(
    amount: Double,
    dueDate: String,
    status: PaymentStatus,
    onPayNow: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Rent Due", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = status.name,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Text(
                    text = "₱${String.format("%,.0f", amount)}",
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Due on $dueDate", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onPayNow,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Pay Now", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
