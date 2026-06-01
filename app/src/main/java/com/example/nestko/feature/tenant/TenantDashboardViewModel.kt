package com.example.nestko.feature.tenant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TenantDashboardUiState(
    val lease: Lease? = null,
    val upcomingPayment: RentPayment? = null,
    val recentMaintenance: List<MaintenanceRequest> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class TenantDashboardViewModel @Inject constructor(
    private val repository: TenantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TenantDashboardUiState())
    val uiState: StateFlow<TenantDashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val tenantId = "t1" // Hardcoded for now
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                repository.getActiveLease(tenantId),
                repository.getUpcomingPayment(tenantId),
                repository.getMaintenanceRequests(tenantId)
            ) { lease, payment, maintenance ->
                _uiState.update {
                    it.copy(
                        lease = lease,
                        upcomingPayment = payment,
                        recentMaintenance = maintenance.take(3),
                        isLoading = false
                    )
                }
            }.collect()
        }
    }
}
