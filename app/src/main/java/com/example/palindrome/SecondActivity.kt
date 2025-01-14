package com.example.palindrome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Get data from Intent (name from the first activity)
        val name = intent.getStringExtra("NAME") ?: "Unknown"

        // Find views
        val nameText = findViewById<TextView>(R.id.nameText)
        val selectedUserText = findViewById<TextView>(R.id.selectedUserText)
        val chooseUserButton = findViewById<Button>(R.id.chooseButton)

        // Set dynamic text
        nameText.text = "$name"

        // Button click to go to Third Activity
        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra("FULL_NAME", name)  // Send the name to ThirdActivity
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected user name from the intent
            val selectedUser = data?.getStringExtra("SELECTED_USER")

            // Find the selectedUserText view and update the text
            val selectedUserText = findViewById<TextView>(R.id.selectedUserText)
            selectedUserText.text = selectedUser // Update the text with the selected user's name
        }
    }
}
