package com.sofit.drinkrecepies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sofit.drinkrecepies.data.model.Drinks

@Dao
interface DaoDrinks {

    @Query("SELECT * FROM Drinks WHERE idDrink =:id AND isFavorite = 1")
    suspend fun getFavoriteDrinkById(id: String): Drinks?

    @Update
    suspend fun update(drinks: Drinks)

    @Insert
    suspend fun insertAll(list: List<Drinks>)

    @Query("SELECT * FROM DRINKS")
    suspend fun getAll() : List<Drinks>

    @Query("DELETE  FROM DRINKS")
    suspend fun delete()

    @Query("SELECT * FROM Drinks WHERE isFavorite = 1")
    suspend fun getAllFavoriteDrink(): List<Drinks>
}