package com.example.nestko.feature.tenant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaseDocumentsScreen(
    onBack: () -> Unit
) {
    val documents = listOf(
        DocumentItem("Lease Agreement", "PDF • 2.4 MB", "Active"),
        DocumentItem("Move-in Checklist", "PDF • 1.1 MB", "Completed"),
        DocumentItem("Property Rules", "PDF • 0.5 MB", "View"),
        DocumentItem("Rent Receipt - May", "PDF • 0.2 MB", "Paid"),
        DocumentItem("Rent Receipt - April", "PDF • 0.2 MB", "Paid")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Documents") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Official Documents", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(documents) { doc ->
                DocumentCard(doc)
            }
        }
    }
}

data class DocumentItem(val name: String, val info: String, val status: String)

@Composable
fun DocumentCard(doc: DocumentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = doc.name, fontWeight = FontWeight.Bold)
                Text(text = doc.info, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Download, contentDescription = "Download", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
