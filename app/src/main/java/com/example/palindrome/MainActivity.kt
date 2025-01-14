package com.example.palindrome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.EditText1)
        val palindromeInput = findViewById<EditText>(R.id.EditText2)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Button Check
        checkButton.setOnClickListener {
            val inputText = palindromeInput.text.toString()

            if (inputText.isBlank()) {
                Toast.makeText(this, "Please enter a sentence to check", Toast.LENGTH_SHORT).show()
            } else {
                if (isPalindrome(inputText)) {
                    showCustomPopup(R.layout.popup_palindrome)
                } else {
                    showCustomPopup(R.layout.popup_notpalindrome)
                }
            }
        }

        // Button Next
        nextButton.setOnClickListener {
            val name = nameInput.text.toString()

            if (name.isBlank()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                // Navigate to SecondActivity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("NAME", name)
                startActivity(intent)
            }
        }
    }

    // Function to check palindrome
    private fun isPalindrome(input: String): Boolean {
        val cleanInput = input.replace("\\s".toRegex(), "").lowercase()
        return cleanInput == cleanInput.reversed()
    }

    // Function to show Custom Popup
    private fun showCustomPopup(layoutResId: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResId, null)
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        // Adjust popup dimensions
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        // Add button functionality in popup
        val continueButton = view.findViewById<Button>(R.id.continueButton)
        continueButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
