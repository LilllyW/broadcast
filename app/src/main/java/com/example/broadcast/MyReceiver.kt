package com.example.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MyReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("alarm", "onReceive")
        var message = ""
        when (intent.action) {
            "Receive" -> message = "BroadCast is here !"
        }

        Toast.makeText(context, "message: $message", Toast.LENGTH_SHORT).show()

        val calendar: Calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        Log.d("alarm", "Show receiver!! ${formatter.format(calendar.time)} actions: ${intent.action}")
        createNotificationChannel(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "abc"
            val descriptionText = "abc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setTicker("登入領獎") // 設定顯示的提示文字
            .setContentTitle("股市爆料同學會") // 設定顯示的標題
            .setContentText("登入簽到已獲獎") // 訊息的詳細內容
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(123, builder.build())
        }

    }

}