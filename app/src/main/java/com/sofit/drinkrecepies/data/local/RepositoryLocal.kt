package com.sofit.drinkrecepies.data.local

import com.sofit.drinkrecepies.data.model.Drinks

class RepositoryLocal(db: AppDatabase) {

    val daoDrinks = db.daoDrinks()

    suspend fun getFavoriteDrinkById(id: String): Drinks? {
        return daoDrinks.getFavoriteDrinkById(id)
    }

    suspend fun update(drinks: Drinks) {
        daoDrinks.update(drinks)
    }

    suspend fun insertAll(list: List<Drinks>) {
        daoDrinks.insertAll(list)
    }

    suspend fun getAll(): List<Drinks> {
        return daoDrinks.getAll()
    }

    suspend fun delete() {
        daoDrinks.delete()
    }

    suspend fun getAllFavoriteDrinks(): List<Drinks> {
        return daoDrinks.getAllFavoriteDrink()
    }

}