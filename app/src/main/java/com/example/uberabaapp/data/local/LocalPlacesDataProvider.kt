package com.example.uberabaapp.data.local

import com.example.uberabaapp.R
import com.example.uberabaapp.data.Categorie
import com.example.uberabaapp.data.Place

object LocalPlacesDataProvider {
    val allPlaces = listOf<Place>(
        Place(
            id = 0L,
            title = R.string.place_0_title,
            description = R.string.place_0_desc,
            image = R.drawable.place_0_image,
            categorie = Categorie.Study
        ),
        Place(
            id = 1L,
            title = R.string.place_1_title,
            description = R.string.place_1_desc,
            image = R.drawable.place_1_image,
            categorie = Categorie.Eat
        ),
        Place(
            id = 2L,
            title = R.string.place_2_title,
            description = R.string.place_2_desc,
            image = R.drawable.place_2_image,
            categorie = Categorie.Fun
        )
    )
}