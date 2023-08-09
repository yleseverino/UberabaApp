package com.example.uberabaapp.ui

import androidx.lifecycle.ViewModel
import com.example.uberabaapp.data.Categorie
import com.example.uberabaapp.data.Place
import com.example.uberabaapp.data.local.LocalPlacesDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UberabaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UberabaUiState())
    val uiState: StateFlow<UberabaUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val placesData = LocalPlacesDataProvider.allPlaces
        val placesTemp: Map<Categorie, List<Place>> = placesData.groupBy { it.categorie }
        val places: Map<Categorie, List<Place>> =
            placesTemp + mapOf(Pair(Categorie.Home, placesData))
        _uiState.value = UberabaUiState(
            placesByCategory = places,
        )
    }

    fun updateCurrentCategory(category: Categorie) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }
}