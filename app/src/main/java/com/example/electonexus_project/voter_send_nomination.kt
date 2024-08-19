package com.example.electonexus_project

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class voter_send_nomination : ComponentActivity(){

    private lateinit var fbrefacc : DatabaseReference
    private lateinit var fbrefelc : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_send_nomination)
        val un = getCredentialsFile()

        val eidtext : TextView = findViewById(R.id.Vsneid)
        val nametext : TextView = findViewById(R.id.Vsnname)


        fbrefacc = FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account")
        fbrefelc = FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Election/")

    }
    private fun getCredentialsFile() :String {
        try {
            // Define the file name
            val fileName = "credentials.txt"
            //val votername : TextView = findViewById(R.id.voterdbname)
           // val voterun : TextView = findViewById(R.id.voterdbun)
            // Open the file for reading
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            // Initialize variables to hold the username and password
            var username: String? = null
            var Accname: String? = null

            // Read the file line by line
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    when {
                        it.startsWith("Username:") -> {
                            username = it.substringAfter("Username:").trim()
                            return username!!
                            //un.setText("ELECTION NAME : $username")
                        }
                        it.startsWith("Acctype:") -> {
                          //  Acctype = it.substringAfter("Acctype:").trim()
                        }
                    }
                }
            }

            // Close the reader
            bufferedReader.close()

            /*if (username != null && Acctype != null) {
                Toast.makeText(this, "Username: $username", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Password: $Acctype", Toast.LENGTH_LONG).show()
            } else {
                // If either username or password is missing
                Toast.makeText(this, "Incomplete credentials found", Toast.LENGTH_SHORT).show()
            }*/

        } catch (e: Exception) {
            // If the file is not found or other errors occur
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
        return ""
    }
}