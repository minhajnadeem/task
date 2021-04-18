package com.sofit.drinkrecepies.data

class RepositoryRemote(val apiService: ApiService) {

    suspend fun getDrinksByName(name:String) = apiService.searchByName(name)
    suspend fun getDrinksByAlphabet(name:String) = apiService.searchByAlphabet(name)
}