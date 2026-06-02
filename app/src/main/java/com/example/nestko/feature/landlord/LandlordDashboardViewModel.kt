package com.example.nestko.feature.landlord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LandlordDashboardUiState(
    val summary: PortfolioSummary? = null,
    val revenueData: List<Pair<String, Double>> = emptyList(),
    val properties: List<Property> = emptyList(),
    val recentActivity: List<MaintenanceRequest> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class LandlordDashboardViewModel @Inject constructor(
    private val repository: LandlordRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LandlordDashboardUiState())
    val uiState: StateFlow<LandlordDashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val landlordId = "l1"
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                repository.getPortfolioSummary(landlordId),
                repository.getMonthlyRevenueData(landlordId),
                propertyRepository.getProperties(),
                repository.getMaintenanceRequests(landlordId)
            ) { summary, revenue, propertiesList, maintenance ->
                _uiState.update {
                    it.copy(
                        summary = summary,
                        revenueData = revenue,
                        properties = propertiesList.take(3),
                        recentActivity = maintenance.take(3),
                        isLoading = false
                    )
                }
            }.collect()
        }
    }
}
