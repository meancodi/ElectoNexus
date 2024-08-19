package com.example.electonexus_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : ComponentActivity() {

    private lateinit var signupButton: Button
    private lateinit var loginButton: Button

    private lateinit var un: EditText
    private lateinit var pw: EditText

    private lateinit var fbref: DatabaseReference

    var type : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        un = findViewById(R.id.LEmailAddress)
        pw = findViewById(R.id.LPassword)
        loginButton = findViewById(R.id.LLoginButton)
        signupButton = findViewById(R.id.LSignupButton)



        loginButton.setOnClickListener {
            checkUsername()
        }
        signupButton.setOnClickListener {
            Intent(this, Account_type::class.java).also { startActivity(it) }
        }

    }

    private fun checkUsername() {
        var b = true
        val username = un.text.toString()
        val password = pw.text.toString()
        fbref =
            FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Account")

        if (username.isEmpty()) {
            un.error = "Please enter Username"
            b = false
        }
        if (password.isEmpty()) {
            pw.error = "Please enter password"
            b = false
        }
        if (b) {
            fbref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val tpw = dataSnapshot.child("pw").value
                        if (tpw == password) {
                            Toast.makeText(
                                this@MainActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            type = dataSnapshot.child("atype").value.toString()
                            if(type=="A"){
                                Toast.makeText(this@MainActivity, "Admin Login", Toast.LENGTH_SHORT).show()
                                saveCredentialsToFile(username, "A")
                                Intent(this@MainActivity, admin_dashboard::class.java).also { startActivity(it) }
                            }
                            else if(type=="V"){
                                Toast.makeText(this@MainActivity, "Login to Voter", Toast.LENGTH_SHORT).show()
                                Intent(this@MainActivity, voter_sign_up::class.java).also { startActivity(it) }
                            }

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Invalid Password",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Username Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()

                }

            })
        }

    }
    private fun saveCredentialsToFile(username: String, Acctype: String) {
        try {
            // Define the file name and directory
            val fileName = "credentials.txt"
            val fileOutputStream: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)

            // Write username and password to the file
            outputStreamWriter.write("Username: $username\n")
            outputStreamWriter.write("Acctype: $Acctype\n")

            // Close the writer
            outputStreamWriter.close()

            // Notify the user that the file has been created
            Toast.makeText(this, "Credentials saved to file", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            // Show an error message if file operations fail
            Toast.makeText(this, "Failed to save credentials", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCredentialsFile() {
        try {
            // Define the file name
            val fileName = "credentials.txt"

            // Check if the file exists
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String?

            // Read the file line by line
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }

            // Close the reader
            bufferedReader.close()

            // Display the content (for demonstration)
            Toast.makeText(this, "File Content:\n${stringBuilder.toString()}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            // If the file is not found or other errors occur
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun readCredentialsFile() {
        try {
            // Define the file name
            val fileName = "credentials.txt"

            // Check if the file exists
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String?

            // Read the file line by line
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }

            // Close the reader
            bufferedReader.close()

            // Display the content (for demonstration)

            Toast.makeText(this, "File Content:\n${stringBuilder.toString()}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            // If the file is not found or other errors occur
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
    }
}



