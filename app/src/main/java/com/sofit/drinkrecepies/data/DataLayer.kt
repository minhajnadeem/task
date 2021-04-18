package com.sofit.drinkrecepies.data

import android.content.Context
import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.data.local.RepositoryLocal
import com.sofit.drinkrecepies.data.model.Drinks
import com.sofit.drinkrecepies.data.model.ResponseModel
import com.sofit.drinkrecepies.utils.Constants.ERROR_INTERNET
import retrofit2.Response
import java.lang.Exception

class DataLayer(
    private val repositoryRemote: RepositoryRemote,
    private val repositoryLocal: RepositoryLocal,
    private val context: Context
) {

    suspend fun getDrinksByName(name: String): Resource<ResponseModel?> {
        return try {
            val response = repositoryRemote.getDrinksByName(name)
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(ERROR_INTERNET)
        }

    }

    suspend fun getDrinksByAlphabet(name: String): Resource<ResponseModel?> {
        return try {
            val response = repositoryRemote.getDrinksByAlphabet(name)
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(ERROR_INTERNET)
        }
    }

    suspend fun getFavoriteDrinkById(id: String): Drinks? {
        return repositoryLocal.getFavoriteDrinkById(id)
    }

    suspend fun updateDrink(drinks: Drinks) {
        repositoryLocal.update(drinks)
    }

    suspend fun saveAllData(list: List<Drinks>) {
        repositoryLocal.insertAll(list)
    }

    suspend fun fetchAllData(): List<Drinks> {
        return repositoryLocal.getAll()
    }

    suspend fun getAllFavoriteDrink(): List<Drinks> {
        return repositoryLocal.getAllFavoriteDrinks()
    }

    suspend fun delete() {
        repositoryLocal.delete()
    }

    private fun <T> processResponse(response: Response<T>): Resource<T?> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(
                context.resources.getString(R.string.str_api_error),
                statusCode = response.code()
            )
        }
    }
}