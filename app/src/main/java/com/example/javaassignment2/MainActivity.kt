
/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * MainActivity: Login screen activity that validates user input,
 * persists email in SharedPreferences on login, and navigates to Dashboard or Registration.
 */
package com.example.javaassignment2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Called when the activity is first created.
     * Initializes UI elements and sets onClick listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // If email exists in preferences, user is logged in, so skip login screen
        if (sharedPreferences.contains("email")) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        // Find views by ID
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonRegister)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validate inputs before proceeding
            if(validateLogin(email, password)) {
                // Save email to SharedPreferences for session persistence
                sharedPreferences.edit().putString("email", email).apply()
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }

        registerButton.setOnClickListener {
            // Navigate to registration screen
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    /**
     * Validates the login form fields.
     * @param email User input email
     * @param password User input password
     * @return true if validation passes; false otherwise
     */
    private fun validateLogin(email: String, password: String): Boolean {
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!email.contains("@")) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.length < 4) {
            Toast.makeText(this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
