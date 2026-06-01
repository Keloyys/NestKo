package com.example.nestko.feature.tenant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MaintenanceUiState(
    val requests: List<MaintenanceRequest> = emptyList(),
    val isLoading: Boolean = false,
    val submissionSuccess: Boolean = false
)

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val repository: TenantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MaintenanceUiState())
    val uiState: StateFlow<MaintenanceUiState> = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    private fun loadRequests() {
        val tenantId = "t1"
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getMaintenanceRequests(tenantId).collect { requests ->
                _uiState.update { it.copy(requests = requests, isLoading = false) }
            }
        }
    }

    fun submitRequest(category: String, description: String, priority: Priority) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val newRequest = MaintenanceRequest(
                id = java.util.UUID.randomUUID().toString(),
                unitId = "u1",
                tenantId = "t1",
                category = category,
                description = description,
                priority = priority
            )
            repository.submitMaintenanceRequest(newRequest)
            _uiState.update { it.copy(isLoading = false, submissionSuccess = true) }
        }
    }

    fun resetSubmissionStatus() {
        _uiState.update { it.copy(submissionSuccess = false) }
    }
}
