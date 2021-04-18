package com.sofit.drinkrecepies.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sofit.drinkrecepies.data.model.Drinks
import com.sofit.drinkrecepies.ui.fragments.home.EnumSearchType
import com.sofit.drinkrecepies.utils.SingleLiveEvent

class SharedViewModel : BaseViewModel() {

    val eventFetchDrinksByName = SingleLiveEvent<String>()
    val eventFetchDrinksByAlphabet = SingleLiveEvent<String>()
    val eventResultDrinksByName = SingleLiveEvent<List<Drinks>>()
    val eventDrinkFavorite = SingleLiveEvent<Drinks>()
    val liveFavorites = MutableLiveData<List<Drinks>>()
    var enumSearchType = EnumSearchType.NAME
}