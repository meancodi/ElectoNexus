package com.example.electonexus_project

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.firebase.components.Component
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class admin_dashboard : ComponentActivity() {

    //private lateinit var un: TextView
    //private lateinit var Acctype: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    // un = findViewById(R.id.electionname)

        setContentView(R.layout.activity_admin_dashboard)
        setCredentialsFile()
    }

    @SuppressLint("SetTextI18n")
    private fun setCredentialsFile() {
        try {
            // Define the file name
            val fileName = "credentials.txt"
            val un : TextView = findViewById(R.id.electionname)
            // Open the file for reading
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            // Initialize variables to hold the username and password
            var username: String? = null
            var Acctype: String? = null

            // Read the file line by line
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    when {
                        it.startsWith("Username:") -> {
                            username = it.substringAfter("Username:").trim()
                            un.setText("ELECTION NAME : $username")
                        }
                        it.startsWith("Acctype:") -> {
                            Acctype = it.substringAfter("Acctype:").trim()
                        }
                    }
                }
            }

            // Close the reader
            bufferedReader.close()

            // Display the username and password in separate Toast messages
            if (username != null && Acctype != null) {
                Toast.makeText(this, "Username: $username", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Password: $Acctype", Toast.LENGTH_LONG).show()
            } else {
                // If either username or password is missing
                Toast.makeText(this, "Incomplete credentials found", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            // If the file is not found or other errors occur
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
    }

}
