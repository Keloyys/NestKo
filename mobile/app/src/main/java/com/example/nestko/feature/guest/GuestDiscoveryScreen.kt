package com.example.nestko.feature.guest

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nestko.core.ui.FeaturedCarousel
import com.example.nestko.core.ui.FilterChipGroup
import com.example.nestko.core.ui.PropertyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestDiscoveryScreen(
    onPropertyClick: (String) -> Unit,
    onNotifications: () -> Unit,
    viewModel: GuestDiscoveryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Find your home in",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Cebu Area",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNotifications) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                    onSearch = { },
                    active = false,
                    onActiveChange = { },
                    placeholder = { Text("Search location, property...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp)
                ) { }
            }

            item {
                FilterChipGroup(
                    filters = listOf("All", "Condo", "House", "Apartment", "Studio"),
                    selectedFilter = uiState.selectedFilter,
                    onFilterSelected = { viewModel.onFilterSelected(it) }
                )
            }

            item {
                Column {
                    PaddingValues(horizontal = 16.dp).let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Featured Properties",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = { /* TODO */ }) {
                                Text("See All", color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    FeaturedCarousel(
                        properties = uiState.featuredProperties,
                        onPropertyClick = { onPropertyClick(it.id) }
                    )
                }
            }

            item {
                Text(
                    text = "Nearby Properties",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(uiState.allProperties) { property ->
                PropertyCard(
                    property = property,
                    onClick = { onPropertyClick(property.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
