package com.example.nestko.feature.guest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.Property
import com.example.nestko.core.domain.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GuestDiscoveryUiState(
    val featuredProperties: List<Property> = emptyList(),
    val allProperties: List<Property> = emptyList(),
    val isLoading: Boolean = false,
    val selectedFilter: String = "All",
    val searchQuery: String = ""
)

@HiltViewModel
class GuestDiscoveryViewModel @Inject constructor(
    private val repository: PropertyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GuestDiscoveryUiState())
    val uiState: StateFlow<GuestDiscoveryUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                repository.getFeaturedProperties(),
                repository.getProperties()
            ) { featured, all ->
                _uiState.update { 
                    it.copy(
                        featuredProperties = featured,
                        allProperties = all,
                        isLoading = false
                    )
                }
            }.collect()
        }
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
        // In a real app, we might trigger a repository call here
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            repository.searchProperties(query).collect { results ->
                _uiState.update { it.copy(allProperties = results) }
            }
        }
    }
}
