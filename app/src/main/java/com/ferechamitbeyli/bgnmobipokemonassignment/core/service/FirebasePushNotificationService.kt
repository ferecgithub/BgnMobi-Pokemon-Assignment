package com.ferechamitbeyli.bgnmobipokemonassignment.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebasePushNotificationService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    companion object {
        const val PUSH_NOTIFICATION_CHANNEL_ID = "pokemon_push_channel_id"
        const val PUSH_NOTIFICATION_CHANNEL_NAME = "pokemon_push_channel"
        const val PUSH_NOTIFICATION_ID = 1
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val text = remoteMessage.notification?.body

        createPushNotificationChannel()
        val notification = NotificationCompat.Builder(this, PUSH_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_pokeball)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(PUSH_NOTIFICATION_ID, notification.build())

        super.onMessageReceived(remoteMessage)
    }

    private fun createPushNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PUSH_NOTIFICATION_CHANNEL_ID,
                PUSH_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}