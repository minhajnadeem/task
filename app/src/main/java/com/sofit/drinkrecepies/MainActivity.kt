package com.sofit.drinkrecepies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.gson.Gson
import com.sofit.drinkrecepies.data.ApiService
import com.sofit.drinkrecepies.data.DataLayer
import com.sofit.drinkrecepies.data.RepositoryRemote
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .build()
    var retrofit: Retrofit
    var apiService: ApiService
    var dataLayer: DataLayer

    init {
        retrofit = Retrofit.Builder().baseUrl("https://www.thecocktaildb.com").addConverterFactory(
            GsonConverterFactory.create(
                Gson()
            )
        ).client(okHttpClient).build()
        apiService = retrofit.create(ApiService::class.java)
        dataLayer = DataLayer(RepositoryRemote(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}