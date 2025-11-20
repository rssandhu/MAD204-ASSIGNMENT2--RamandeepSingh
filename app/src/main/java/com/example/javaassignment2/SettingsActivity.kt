

/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * SettingsActivity: Provides toggle switches for Dark Mode and Notifications.
 * Persists preferences using SharedPreferences and restores selections on reopen.
 */

package com.example.javaassignment2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var notificationsSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Sets up UI switches and restores saved preferences.
     * Saves changes on toggle with user feedback.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        darkModeSwitch = findViewById(R.id.switchDarkMode)
        notificationsSwitch = findViewById(R.id.switchNotifications)

        // Restore saved preferences
        darkModeSwitch.isChecked = sharedPreferences.getBoolean("dark_mode", false)
        notificationsSwitch.isChecked = sharedPreferences.getBoolean("notifications", true)

        // Listen for dark mode toggle and save preference
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
            // Toast feedback
            Toast.makeText(this, if (isChecked) "Dark mode ON" else "Dark mode OFF", Toast.LENGTH_SHORT).show()
            // TODO: Add real theme switching logic if needed
        }

        // Listen for notifications toggle and save preference
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Notifications ON" else "Notifications OFF", Toast.LENGTH_SHORT).show()
        }
    }
}
