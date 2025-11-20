

/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * DashboardActivity: Central hub displaying welcome message and navigation buttons.
 * Includes lifecycle method logging.
 */

package com.example.javaassignment2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var profileButton: Button
    private lateinit var countriesButton: Button
    private lateinit var settingsButton: Button
    private lateinit var logoutButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Initializes UI and sets up button click listeners.
     * Logs lifecycle method onCreate.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DashboardActivity", "onCreate called")
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        welcomeTextView = findViewById(R.id.textViewWelcome)
        profileButton = findViewById(R.id.buttonProfile)
        countriesButton = findViewById(R.id.buttonCountries)
        settingsButton = findViewById(R.id.buttonSettings)
        logoutButton = findViewById(R.id.buttonLogout)

        // Display name if available, else show email
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val displayName = if (!name.isNullOrEmpty()) name else email

        welcomeTextView.text = "Welcome, $displayName"

        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        countriesButton.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        logoutButton.setOnClickListener {
            // Clear user session data on logout
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java)
            // Clear back stack
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("DashboardActivity", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("DashboardActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("DashboardActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("DashboardActivity", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DashboardActivity", "onDestroy called")
    }
}
