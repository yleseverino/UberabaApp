package com.example.uberabaapp.ui

import com.example.uberabaapp.data.Categorie
import com.example.uberabaapp.data.Place

data class UberabaUiState(

    val placesByCategory: Map<Categorie, List<Place>> = emptyMap(),
    val currentCategory: Categorie = Categorie.Home
) {
    val currentCategoryItems: List<Place> by lazy {
        placesByCategory[currentCategory]!!
    }
}