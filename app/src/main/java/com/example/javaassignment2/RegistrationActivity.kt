/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * RegistrationActivity: Screen for new user registration.
 * Validates inputs, saves user data to SharedPreferences, and returns to Login screen.
 */
package com.example.javaassignment2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var programEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Initializes UI components and handles register button click.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        fullNameEditText = findViewById(R.id.editTextFullName)
        emailEditText = findViewById(R.id.editTextEmail)
        ageEditText = findViewById(R.id.editTextAge)
        programEditText = findViewById(R.id.editTextProgram)
        registerButton = findViewById(R.id.buttonRegister)

        registerButton.setOnClickListener {
            if(validateRegistration()) {
                // Save all fields into SharedPreferences
                sharedPreferences.edit().apply {
                    putString("name", fullNameEditText.text.toString())
                    putString("email", emailEditText.text.toString())
                    putInt("age", ageEditText.text.toString().toInt())
                    putString("program", programEditText.text.toString())
                    apply()
                }
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
        }
    }

    /**
     * Validates registration fields for emptiness and correctness.
     * @return true if all validations pass
     */
    private fun validateRegistration(): Boolean {
        val fullName = fullNameEditText.text.toString()
        val email = emailEditText.text.toString()
        val ageStr = ageEditText.text.toString()
        val program = programEditText.text.toString()

        // Check all required fields
        if(fullName.isEmpty() || email.isEmpty() || ageStr.isEmpty() || program.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!email.contains("@")) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }
        // Parse age safely
        val age = try { ageStr.toInt() } catch (e: NumberFormatException) { -1 }
        if(age <= 0) {
            Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
