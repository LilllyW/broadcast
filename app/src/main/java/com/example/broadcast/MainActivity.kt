package com.example.broadcast

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
//            sendBroadcast(Intent(this, MyReceiver::class.java).setAction("Receive"))

            alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmIntent = Intent(this, MyReceiver::class.java).setAction("Receive").let { intent ->
                PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val calendar = Calendar.getInstance()
                .apply {
                    timeInMillis = System.currentTimeMillis()
                }

            alarmManager?.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis + 1000 * 5 * 1,
                alarmIntent
            )

            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            Log.d("alarm", formatter.format(calendar.time))
//            Toast.makeText(this, "Starting Alarm!", Toast.LENGTH_SHORT).show()

        }

        cancel_button.setOnClickListener {
            alarmManager?.cancel(alarmIntent)
            Log.d("alarm", "alarm cancelled!")
            Toast.makeText(this, "Alarm Cancelled!", Toast.LENGTH_SHORT).show()
        }
    }
}