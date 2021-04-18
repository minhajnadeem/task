package com.sofit.drinkrecepies

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.google.gson.Gson
import com.sofit.drinkrecepies.base.SharedViewModel
import com.sofit.drinkrecepies.data.ApiService
import com.sofit.drinkrecepies.data.DataLayer
import com.sofit.drinkrecepies.data.RepositoryRemote
import com.sofit.drinkrecepies.data.local.AppDatabase
import com.sofit.drinkrecepies.data.local.RepositoryLocal
import com.sofit.drinkrecepies.data.local.datastore.DataStoreProvider
import com.sofit.drinkrecepies.receivers.ReceiverBootComplete
import com.sofit.drinkrecepies.receivers.ReceiverNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .build()
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    lateinit var dataLayer: DataLayer
    lateinit var sharedViewModel: SharedViewModel
    lateinit var mViewModel: MainViewModel
    lateinit var dataStoreProvider: DataStoreProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mViewModel.init(dataLayer)
        mViewModel.fetchFavorites()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        observeShareViewModel()
        observeViewModel()
        scheduleNotification(this)
        enableReceiver()
    }

    private fun init() {
        retrofit = Retrofit.Builder().baseUrl("https://www.thecocktaildb.com").addConverterFactory(
            GsonConverterFactory.create(
                Gson()
            )
        ).client(okHttpClient).build()
        apiService = retrofit.create(ApiService::class.java)
        //local
        val db = AppDatabase.getInstance(this)
        dataLayer = DataLayer(RepositoryRemote(apiService), RepositoryLocal(db), this)
        dataStoreProvider = DataStoreProvider(this)
    }

    private fun observeShareViewModel() {
        sharedViewModel.eventFetchDrinksByName.observe(this, Observer {
            mViewModel.fetchDrinksByName(it)
        })
        sharedViewModel.eventFetchDrinksByAlphabet.observe(this, Observer {
            mViewModel.fetchDrinksByAlphabet(it)
        })
        sharedViewModel.eventDrinkFavorite.observe(this, Observer {
            mViewModel.doFavorite(it)
        })
    }

    private fun observeViewModel() {
        mViewModel.eventShowHideProgress.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })
        mViewModel.eventResultSearchByName.observe(this, Observer {
            sharedViewModel.eventResultDrinksByName.value = it
        })

        mViewModel.eventResultFavorites.observe(this, Observer {
            sharedViewModel.liveFavorites.value = it
        })
    }

    companion object {
        fun scheduleNotification(context: Context) {
            val manager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReceiverNotification::class.java)
            var pending = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE)
            if (pending == null) {
                pending = PendingIntent.getBroadcast(context, 0, intent, 0)
                val cal = Calendar.getInstance()
                val current = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, 14)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                if (current.timeInMillis > cal.timeInMillis) {
                    cal.add(Calendar.DAY_OF_MONTH, 1)
                }
                manager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pending
                )
            }
        }
    }

    private fun enableReceiver() {
        val component = ComponentName(this, ReceiverBootComplete::class.java)
        packageManager.setComponentEnabledSetting(
            component,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}