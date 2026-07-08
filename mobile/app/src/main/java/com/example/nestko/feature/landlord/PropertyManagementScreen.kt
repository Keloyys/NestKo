package com.example.nestko.feature.landlord

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nestko.core.ui.PropertyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyManagementScreen(
    onBack: () -> Unit,
    onAddProperty: () -> Unit,
    onPropertyClick: (String) -> Unit,
    viewModel: LandlordDashboardViewModel = hiltViewModel()
) {
    // Reusing the dashboard VM for owned properties for now
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Property Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProperty, containerColor = MaterialTheme.colorScheme.tertiary) {
                Icon(Icons.Default.Add, contentDescription = "Add Property")
            }
        }
    ) { padding ->
        // In a real app, we'd fetch actual owned properties here. 
        // Using a placeholder for the list.
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "My Properties", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            
            items(uiState.properties) { property ->
                PropertyCard(
                    property = property,
                    onClick = { onPropertyClick(property.id) }
                )
            }
        }
    }
}
