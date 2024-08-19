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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class admin_dashboard : ComponentActivity() {

    //private lateinit var un: TextView
    //private lateinit var Acctype: TextView

    private lateinit var fbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    // un = findViewById(R.id.electionname)

        fbref =
            FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Account")

        setContentView(R.layout.activity_admin_dashboard)
        setCredentialsFile()
    }

    @SuppressLint("SetTextI18n")
    private fun setCredentialsFile() {
        try {
            // Define the file name
            val fileName = "credentials.txt"
            val enview : TextView = findViewById(R.id.electionname)
            val eidview : TextView = findViewById(R.id.electionid)
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
                            fbref.child(username!!).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(snapshot.exists()){
                                        val foundun = snapshot.child("en").value
                                        enview.setText("ELECTION NAME : $foundun")
                                        val foundeid = snapshot.child("eid").value
                                        eidview.setText("ELECTION ID : $foundeid")

                                    }
                                    else{
                                        Toast.makeText(this@admin_dashboard,"Not Found in Local File",Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(this@admin_dashboard, "$error", Toast.LENGTH_SHORT).show()
                                }
                            })

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
    /*private fun getEn(un : String): String{
        var foundun: String = ""
        fbref.child(un).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    foundun = snapshot.child("en").value
                }
                else{
                    Toast.makeText(this@admin_dashboard,"Not Found in Local File",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@admin_dashboard, "$error", Toast.LENGTH_SHORT).show()
            }
        })
        Toast.makeText(this@admin_dashboard, "In getEn $foundun", Toast.LENGTH_SHORT).show()

        return foundun
    }*/

}
