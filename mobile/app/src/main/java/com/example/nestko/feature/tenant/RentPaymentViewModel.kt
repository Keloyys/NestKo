package com.example.nestko.feature.tenant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RentPaymentUiState(
    val upcomingPayment: RentPayment? = null,
    val paymentHistory: List<RentPayment> = emptyList(),
    val isLoading: Boolean = false,
    val paymentSuccess: Boolean = false
)

@HiltViewModel
class RentPaymentViewModel @Inject constructor(
    private val repository: TenantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RentPaymentUiState())
    val uiState: StateFlow<RentPaymentUiState> = _uiState.asStateFlow()

    init {
        loadPaymentData()
    }

    private fun loadPaymentData() {
        val tenantId = "t1"
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            combine(
                repository.getUpcomingPayment(tenantId),
                repository.getRecentPayments(tenantId)
            ) { upcoming, history ->
                _uiState.update {
                    it.copy(
                        upcomingPayment = upcoming,
                        paymentHistory = history,
                        isLoading = false
                    )
                }
            }.collect()
        }
    }

    fun processPayment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Mock payment processing delay
            kotlinx.coroutines.delay(2000)
            _uiState.update { it.copy(isLoading = false, paymentSuccess = true) }
        }
    }
}
