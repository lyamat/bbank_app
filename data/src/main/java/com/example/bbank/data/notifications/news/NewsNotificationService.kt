//package com.example.bbank.data.notifications.news
//
//import android.app.NotificationManager
//import android.content.Context
//import androidx.core.app.NotificationCompat
//import androidx.core.os.bundleOf
////import androidx.navigation.NavDeepLinkBuilder
////import com.example.bbank.R
//import com.example.bbank.domain.notifications.NotificationService
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//
//internal class NewsNotificationService @Inject constructor(
//    @ApplicationContext private val context: Context
//) : NotificationService<News> {
//
//    private val notificationManager =
//        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//    companion object {
//        const val NEWS_CHANNEL_ID = "news_channel"
//    }
//
//    override fun showNotification(data: News) {
////        val deepLinkIntent = NavDeepLinkBuilder(context)
////            .setGraph(R.navigation.nav_graph)
////            .setDestination(R.id.newsDetailFragment)
////            .setArguments(bundleOf("news" to data))
////            .createPendingIntent()
////
////        val notification = NotificationCompat.Builder(context, NEWS_CHANNEL_ID)
////            .setSmallIcon(R.drawable.ic_news)
////            .setContentTitle(context.getString(R.string.news_of, data.startDate))
////            .setContentText(data.nameRu)
////            .setContentIntent(deepLinkIntent)
////            .setAutoCancel(true)
////            .build()
////
////        notificationManager.notify(1, notification)
//    }
//}