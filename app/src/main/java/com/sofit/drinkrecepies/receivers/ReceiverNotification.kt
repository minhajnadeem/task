package com.sofit.drinkrecepies.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.sofit.drinkrecepies.BuildConfig
import com.sofit.drinkrecepies.R
import com.sofit.drinkrecepies.data.local.AppDatabase
import com.sofit.drinkrecepies.data.model.Drinks
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class ReceiverNotification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm details"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        context?.let { ctx ->
            val db = AppDatabase.getInstance(ctx)
            var favorites: List<Drinks>
            runBlocking {
                favorites = db.daoDrinks().getAllFavoriteDrink()
            }
            val drinkName: String
            val drinkRecipe: String
            if (favorites.isEmpty()) {
                drinkName = ctx.resources.getString(R.string.str_notification_default)
                drinkRecipe = ""
            } else {
                val random = Random(System.nanoTime())
                val index = (favorites.indices).random(random)
                val drink = favorites[index]
                drinkName = drink.strDrink
                drinkRecipe = drink.strInstructions
            }
            // Create the notification to be shown
            val view = RemoteViews(ctx.packageName, R.layout.item_notification)
            view.setTextViewText(R.id.tvDrinkName, drinkName)
            view.setTextViewText(R.id.tvDrinkRecepie, drinkRecipe)
            view.setImageViewResource(R.id.ivDrink, R.drawable.ic_star_empty)
            val mBuilder = NotificationCompat.Builder(ctx, "AlarmId")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(view)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Get the Notification manager service
            val am = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Generate an Id for each notification
            val id = System.currentTimeMillis() / 1000

            val notification = mBuilder.build()
            val notificationTarget =
                NotificationTarget(context, R.id.ivDrink, view, notification, id.toInt())
            Glide.with(ctx)
                .asBitmap()
                .load("https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg")
                .into(notificationTarget)
            // Show a notification
            am.notify(id.toInt(), notification)
        }
    }
}