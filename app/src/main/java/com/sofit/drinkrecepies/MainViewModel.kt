package com.sofit.drinkrecepies

import androidx.lifecycle.viewModelScope
import com.sofit.drinkrecepies.base.BaseViewModel
import com.sofit.drinkrecepies.data.DataLayer
import com.sofit.drinkrecepies.data.Resource
import com.sofit.drinkrecepies.data.model.Drinks
import com.sofit.drinkrecepies.data.model.ResponseModel
import com.sofit.drinkrecepies.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    lateinit var dataLayer: DataLayer
    val eventResultSearchByName = SingleLiveEvent<List<Drinks>>()
    val eventResultFavorites = SingleLiveEvent<List<Drinks>>()

    fun init(dataLayer: DataLayer) {
        this.dataLayer = dataLayer
    }

    fun fetchDrinksByName(name: String) {
        showLoading()
        viewModelScope.launch {
            val response = dataLayer.getDrinksByName(name)
            handleResponse(response)
            hideLoading()
        }
    }

    fun fetchDrinksByAlphabet(name: String) {
        showLoading()
        viewModelScope.launch {
            val response = dataLayer.getDrinksByAlphabet(name)
            handleResponse(response)
            hideLoading()
        }
    }

    private suspend fun handleResponse(response: Resource<ResponseModel?>) {
        if (response.status == Resource.Status.SUCCESS) {
            response.data?.apply {
                mapData(drinks)
                dataLayer.delete()
                dataLayer.saveAllData(drinks)
                eventResultSearchByName.value = drinks
            }
        } else {
            val localData = dataLayer.fetchAllData()
            eventResultSearchByName.value = localData
        }
    }

    private suspend fun mapData(drinks: List<Drinks>) {
        val listFav = dataLayer.getAllFavoriteDrink()
        listFav.forEach { drink ->
            drinks.map { if (drink.idDrink == it.idDrink) it.isFavorite = true }
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            eventResultFavorites.value = dataLayer.getAllFavoriteDrink()
        }
    }

    fun doFavorite(drinks: Drinks) {
        viewModelScope.launch {
            val fav = dataLayer.getFavoriteDrinkById(drinks.idDrink)
            if (fav == null) {
                drinks.isFavorite = true
                dataLayer.updateDrink(drinks)
            } else {
                drinks.isFavorite = false
                dataLayer.updateDrink(drinks)
            }
            val list = dataLayer.fetchAllData()
            eventResultSearchByName.value = list
            fetchFavorites()
        }
    }
}