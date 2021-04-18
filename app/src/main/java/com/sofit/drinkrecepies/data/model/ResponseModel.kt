package com.sofit.drinkrecepies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ResponseModel(val drinks: List<Drinks>)

@Entity
data class Drinks(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strAlcoholic: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    var isFavorite: Boolean
)