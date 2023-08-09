package com.example.uberabaapp.data

import androidx.annotation.DrawableRes

data class Place(
    val id: Long,
    val title: Int,
    val description: Int,
    val categorie: Categorie,
    @DrawableRes val image: Int
)
