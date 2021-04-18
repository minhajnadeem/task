package com.sofit.drinkrecepies.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sofit.drinkrecepies.MainActivity

class ReceiverBootComplete : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            MainActivity.scheduleNotification(context)
        }
    }
}