package com.example.nestko.feature.guest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestko.core.domain.Property
import com.example.nestko.core.domain.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PropertyDetailUiState(
    val property: Property? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val repository: PropertyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val propertyId: String = checkNotNull(savedStateHandle["propertyId"])

    private val _uiState = MutableStateFlow(PropertyDetailUiState())
    val uiState: StateFlow<PropertyDetailUiState> = _uiState.asStateFlow()

    init {
        loadProperty()
    }

    private fun loadProperty() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getPropertyById(propertyId).collect { property ->
                _uiState.update { it.copy(property = property, isLoading = false) }
            }
        }
    }

    fun toggleSave() {
        _uiState.update { it.copy(isSaved = !it.isSaved) }
    }
}
