package com.sofit.drinkrecepies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sofit.drinkrecepies.data.model.Drinks

@Database(entities = arrayOf(Drinks::class), version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoDrinks(): DaoDrinks

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "db_drinks")
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}