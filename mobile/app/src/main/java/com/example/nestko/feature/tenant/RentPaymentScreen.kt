package com.example.nestko.feature.tenant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nestko.core.domain.RentPayment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentPaymentScreen(
    onBack: () -> Unit,
    viewModel: RentPaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.paymentSuccess) {
        PaymentSuccessScreen(onBack = onBack)
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pay Rent") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { viewModel.processPayment() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Confirm Payment", fontWeight = FontWeight.Bold)
                }
            }
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Current Billing Period", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = "₱${String.format("%,.0f", uiState.upcomingPayment?.amount ?: 0.0)}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Due on ${uiState.upcomingPayment?.dueDate?.toLocalDate()}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                Text(text = "Payment Method", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                PaymentMethodSelector()
            }

            item {
                Text(text = "Payment History", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(uiState.paymentHistory) { payment ->
                PaymentHistoryItem(payment)
            }
        }
    }
}

@Composable
fun PaymentMethodSelector() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("BDO Unibank", fontWeight = FontWeight.Bold)
                Text("**** **** **** 4242", style = MaterialTheme.typography.labelMedium)
            }
            RadioButton(selected = true, onClick = null)
        }
    }
}

@Composable
fun PaymentHistoryItem(payment: RentPayment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Receipt, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = payment.dueDate.toLocalDate().toString(), fontWeight = FontWeight.Bold)
            Text(text = payment.method ?: "Unknown", style = MaterialTheme.typography.labelMedium)
        }
        Text(
            text = "₱${String.format("%,.0f", payment.amount)}",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF22C55E)
        )
    }
}

@Composable
fun PaymentSuccessScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF22C55E),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Payment Successful!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Thank you! Your rent has been paid.", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, shape = RoundedCornerShape(28.dp)) {
                Text("Back to Dashboard")
            }
        }
    }
}
