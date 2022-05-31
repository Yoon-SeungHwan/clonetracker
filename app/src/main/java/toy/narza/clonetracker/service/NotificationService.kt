package toy.narza.clonetracker.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.AppDataBase

class NotificationService(val context: Context) {

    private val CHANNEL_NAME = "NzCloneTracker Service"

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: NotificationService? = null

        fun getInstance(context: Context): NotificationService {
            return instance ?: synchronized(this) {
                instance ?: NotificationService(context)
            }
        }
    }

    fun notify(message: String) {
        val builder = NotificationCompat.Builder(context, context.getString(R.string.app_name))
            .setSmallIcon(R.drawable.diablo_indicator)
            .setContentTitle(context.resources.getString(R.string.title_notification))
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        builder.build()

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

    fun createNotification(): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                context.getString(R.string.app_name)
            )
        } else {
            ""
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }

        builder.setContentTitle("Notification Title")
        builder.setContentText("Notification Text")

        return builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String): String {
        val chan = NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}