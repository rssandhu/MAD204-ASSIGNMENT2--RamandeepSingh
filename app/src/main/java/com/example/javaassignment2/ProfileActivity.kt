

/**
 *     Course: [MAD204-01] - Lab Assignment 2
 *     Student: Ramandeep Singh, A00194321
 *     Date: 2025-11-20
 *
 * ProfileActivity: Displays and allows editing of user profile details stored in SharedPreferences.
 * Supports toggling between read-only and edit modes.
 */

package com.example.javaassignment2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    // UI elements for display
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var programTextView: TextView

    // UI elements for editing
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var programEditText: EditText

    private lateinit var editProfileButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    private var editing = false // Tracks edit mode state

    /**
     * Initializes UI and loads profile data.
     * Handles edit/save button logic.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        nameTextView = findViewById(R.id.textViewName)
        emailTextView = findViewById(R.id.textViewEmail)
        ageTextView = findViewById(R.id.textViewAge)
        programTextView = findViewById(R.id.textViewProgram)
        editProfileButton = findViewById(R.id.buttonEditProfile)

        nameEditText = findViewById(R.id.editTextName)
        ageEditText = findViewById(R.id.editTextAge)
        programEditText = findViewById(R.id.editTextProgram)

        loadProfile()

        editProfileButton.setOnClickListener {
            if (!editing) {
                // Enable edit mode
                enableEditing(true)
                editProfileButton.text = "Save"
            } else {
                // Save updated profile after validation
                if (validateAndSaveProfile()) {
                    enableEditing(false)
                    editProfileButton.text = "Edit Profile"
                    loadProfile()
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Loads profile data from SharedPreferences and displays it.
     */
    private fun loadProfile() {
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val age = sharedPreferences.getInt("age", 0)
        val program = sharedPreferences.getString("program", "")

        nameTextView.text = "Name: $name"
        emailTextView.text = "Email: $email"
        ageTextView.text = "Age: $age"
        programTextView.text = "Program: $program"

        // Fill edit texts for editing mode
        nameEditText.setText(name)
        ageEditText.setText(if (age > 0) age.toString() else "")
        programEditText.setText(program)

        // Default to read-only mode
        enableEditing(false)
    }

    /**
     * Enables or disables editing mode.
     * @param enable true to enable editing; false to disable
     */
    private fun enableEditing(enable: Boolean) {
        editing = enable
        nameEditText.isEnabled = enable
        ageEditText.isEnabled = enable
        programEditText.isEnabled = enable

        // Show/hide edit texts or textviews depending on mode
        nameEditText.visibility = if (enable) TextView.VISIBLE else TextView.GONE
        ageEditText.visibility = if (enable) TextView.VISIBLE else TextView.GONE
        programEditText.visibility = if (enable) TextView.VISIBLE else TextView.GONE

        nameTextView.visibility = if (enable) TextView.GONE else TextView.VISIBLE
        ageTextView.visibility = if (enable) TextView.GONE else TextView.VISIBLE
        programTextView.visibility = if (enable) TextView.GONE else TextView.VISIBLE
    }

    /**
     * Validates input fields and saves updated profile to SharedPreferences.
     * @return true if validation and saving succeeded
     */
    private fun validateAndSaveProfile(): Boolean {
        val newName = nameEditText.text.toString()
        val newAgeStr = ageEditText.text.toString()
        val newProgram = programEditText.text.toString()

        if (newName.isEmpty() || newAgeStr.isEmpty() || newProgram.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        val newAge = try { newAgeStr.toInt() } catch (e: NumberFormatException) { -1 }
        if (newAge <= 0) {
            Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_SHORT).show()
            return false
        }

        sharedPreferences.edit().apply {
            putString("name", newName)
            putInt("age", newAge)
            putString("program", newProgram)
            apply()
        }
        return true
    }
}
