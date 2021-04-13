package com.sofit.drinkrecepies.data

import com.sofit.drinkrecepies.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH)
    suspend fun searchByName(@Query("s") s: String): Response<ResponseModel>

    @GET(SEARCH)
    suspend fun searchByAlphabet(@Query("f") f: String): Response<ResponseModel>

    companion object {
        const val API = "api/json"
        const val V = "/v1/1"

        const val SEARCH = "$API$V/search.php"
    }
}