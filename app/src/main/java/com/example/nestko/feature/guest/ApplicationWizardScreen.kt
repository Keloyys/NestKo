package com.example.nestko.feature.guest

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationWizardScreen(
    propertyId: String,
    onBack: () -> Unit,
    onComplete: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rental Application") },
                navigationIcon = {
                    IconButton(onClick = { if (currentStep > 1) currentStep-- else onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (currentStep > 1) {
                    OutlinedButton(
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text("Back")
                    }
                }
                Button(
                    onClick = {
                        if (currentStep < totalSteps) currentStep++ else onComplete()
                    },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(if (currentStep == totalSteps) "Submit Application" else "Next Step")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            StepIndicator(currentStep, totalSteps)
            
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(targetState = currentStep, label = "step_content") { step ->
                Column(modifier = Modifier.padding(16.dp)) {
                    when (step) {
                        1 -> PersonalInfoStep()
                        2 -> EmploymentStep()
                        3 -> RentalHistoryStep()
                        4 -> ReferencesStep()
                        5 -> ReviewStep()
                    }
                }
            }
        }
    }
}

@Composable
fun StepIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val step = index + 1
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        color = if (step <= currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}

@Composable
fun PersonalInfoStep() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Personal Information", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Date of Birth") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nationality") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Government ID Number") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun EmploymentStep() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Employment & Income", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Current Employer") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Position") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Monthly Income") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun RentalHistoryStep() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Rental History", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Previous Address") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Previous Landlord Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Reason for Moving") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ReferencesStep() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("References", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Reference 1", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contact Number") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Text("Reference 2", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contact Number") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ReviewStep() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Review & Submit", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Please review all information before submitting. By submitting, you agree to a background check.", style = MaterialTheme.typography.bodyMedium)
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Summary", fontWeight = FontWeight.Bold)
                Text("Name: John Doe")
                Text("Employer: Tech Cebu")
                Text("Income: ₱50,000")
            }
        }
    }
}
