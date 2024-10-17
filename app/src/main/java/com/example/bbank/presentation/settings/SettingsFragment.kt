package com.example.bbank.presentation.settings

//import com.example.bbank.data.worker.NewsNotificationWorker
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.work.WorkManager
import com.example.bbank.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        monitorPreferences()
    }

    private fun monitorPreferences() =
        findPreference<SwitchPreferenceCompat>("newsNotifications")
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
//                    startNewsNotificationWorker()
                } else {
                    cancelUniqueWorkByName(uniqueWorkName = NEWS_UNIQUE_WORK_NAME)
                }
                true
            }

//    private fun startNewsNotificationWorker() {
//        val newsGettingPeriodicWork =
//            PeriodicWorkRequestBuilder<NewsNotificationWorker>(15, TimeUnit.MINUTES)
//                .setConstraints(
//                    Constraints.Builder()
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
//                        .setRequiresBatteryNotLow(true)
//                        .build()
//                )
//                .setInitialDelay(5, TimeUnit.SECONDS)
//                .build()
//
//        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
//            NEWS_UNIQUE_WORK_NAME,
//            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
//            newsGettingPeriodicWork
//        )
//    }

    private fun cancelUniqueWorkByName(uniqueWorkName: String) =
        WorkManager.getInstance(requireContext()).cancelUniqueWork(uniqueWorkName)

    private companion object {
        const val NEWS_UNIQUE_WORK_NAME = "NewsNotificationWorker"
    }
}