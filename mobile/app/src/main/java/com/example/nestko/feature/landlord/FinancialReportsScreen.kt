package com.example.nestko.feature.landlord

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nestko.core.domain.TransactionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialReportsScreen(
    onBack: () -> Unit,
    viewModel: LandlordDashboardViewModel = hiltViewModel()
) {
    // In a real app, use a dedicated FinancialViewModel
    val transactions = listOf(
        FinancialItem("Rent - Unit 1201", "₱25,000", TransactionType.INCOME),
        FinancialItem("Repair - Kitchen", "₱1,500", TransactionType.EXPENSE),
        FinancialItem("Rent - Unit 808", "₱35,000", TransactionType.INCOME),
        FinancialItem("Insurance - Annual", "₱12,000", TransactionType.EXPENSE),
        FinancialItem("Rent - Unit A1", "₱15,000", TransactionType.INCOME)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Financial Reports") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Net Profit", style = MaterialTheme.typography.labelMedium, color = Color(0xFF2E7D32))
                            Text("₱185,000", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        }
                    }
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Expenses", style = MaterialTheme.typography.labelMedium, color = Color(0xFFC62828))
                            Text("₱42,000", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                        }
                    }
                }
            }

            item {
                Text(text = "Recent Transactions", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(transactions) { item ->
                TransactionRow(item)
            }
        }
    }
}

data class FinancialItem(val title: String, val amount: String, val type: TransactionType)

@Composable
fun TransactionRow(item: FinancialItem) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (item.type == TransactionType.INCOME) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
            modifier = Modifier.size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (item.type == TransactionType.INCOME) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = null,
                    tint = if (item.type == TransactionType.INCOME) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = "24 May 2026", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
        }
        Text(
            text = (if (item.type == TransactionType.INCOME) "+" else "-") + item.amount,
            fontWeight = FontWeight.Bold,
            color = if (item.type == TransactionType.INCOME) Color(0xFF2E7D32) else Color(0xFFC62828)
        )
    }
}
