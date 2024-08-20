package com.example.electonexus_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class voter_dashboard : ComponentActivity(){

    private lateinit var fbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voter_dashboard)
        setCredentialsFile()
        val eregistor : Button = findViewById(R.id.Velectionregistor)
        val castvotebutton : Button = findViewById(R.id.Vcastvotebutton)
        eregistor.setOnClickListener {
            Intent(this, voter_election_registor::class.java).also{startActivity(it) }
        }
        castvotebutton.setOnClickListener {
            val intent = Intent(this, voter_cast_vote::class.java).apply {
                putExtra("EXTRA_TEXT", "lanjaaa") // Put the string data as an extra
            }
            startActivity(intent)

        }



        val vcandidatebutton : Button = findViewById(R.id.Vcandidateapply)
        vcandidatebutton.setOnClickListener {
            Intent(this, voter_send_nomination::class.java).also { startActivity(it) }
        }

    }
    private fun setCredentialsFile() {
        try {
            // Define the file name
            val fileName = "credentials.txt"
            val votername : TextView = findViewById(R.id.voterdbname)
            val voterun : TextView = findViewById(R.id.voterdbun)
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
                            voterun.setText(username)


                        }
                        it.startsWith("elname:") -> {
                            Accname = it.substringAfter("elname:").trim()
                            votername.setText(Accname)
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
    }
}