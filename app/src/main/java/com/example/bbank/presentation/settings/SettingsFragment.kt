package com.example.bbank.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.bbank.R
import com.example.core.domain.news.SyncNewsScheduler
import com.example.news.data.notifications.NewsNotificationChannelFactory.Companion.NEWS_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@AndroidEntryPoint
internal class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var syncNewsScheduler: SyncNewsScheduler

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        monitorPreferences()
    }

    private fun monitorPreferences() {
        val newsSyncPref = findPreference<SwitchPreferenceCompat>("newsSync")
        val newsNotificationsPref = findPreference<Preference>("newsNotifications")

        newsNotificationsPref?.setOnPreferenceClickListener {
            openNotificationChannelSettings(NEWS_CHANNEL_ID)
            true
        }

        newsSyncPref?.setOnPreferenceChangeListener { _, newValue ->
            viewLifecycleOwner.lifecycleScope.launch {
                if (newValue as Boolean) {
                    syncNewsScheduler.scheduleSync(
                        SyncNewsScheduler.SyncType.FetchNews(24.hours)
                    )
                    newsSyncPref.isChecked = true
                } else {
                    syncNewsScheduler.cancelSync()
                    newsSyncPref.isChecked = false
                }
            }
            true
        }
    }

    private fun openNotificationChannelSettings(channelId: String) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
        }
        startActivity(intent)
    }
}