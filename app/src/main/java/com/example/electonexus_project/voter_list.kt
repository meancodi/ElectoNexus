package com.example.electonexus_project;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.electonexus_project.R
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class voter_list : ComponentActivity() {
    private var lastTextViewId: Int? = R.id.textView4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voter_list)

        val un : String = getCredentialsFile()

        val containerLayout: ConstraintLayout = findViewById(R.id.containervoterlist)
        var i : Int
        for (i in 0..20){
            createTextView("")
            /*val newTextView = TextView(this).apply {
                id = View.generateViewId() // Generate a unique ID
                text = "New TextView #${i + 1}"
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }

            containerLayout.addView(newTextView)

            applyConstraintsToView(containerLayout, newTextView)

            // Update the last IDs
            lastTextViewId = newTextView.id*/

        }



    }
    private fun createTextView(name : String){
        val containerLayout: ConstraintLayout = findViewById(R.id.containervoterlist)
        val newTextView = TextView(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = "$name"
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }

        containerLayout.addView(newTextView)

        applyConstraintsToView(containerLayout, newTextView)

        // Update the last IDs
        lastTextViewId = newTextView.id
    }
    private fun applyConstraintsToView(parent: ConstraintLayout, textView: TextView) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)

        // Apply constraints to the TextView

            // Position the new TextView below the last TextView
        constraintSet.connect(textView.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)

        constraintSet.connect(textView.id, ConstraintSet.START, parent.id, ConstraintSet.START,dpToPx(60f) )
        // constraintSet.connect(textView.id, ConstraintSet.END, parent.id, ConstraintSet.END, 16)

        // Apply constraints to the Button


        constraintSet.applyTo(parent)
    }
    private fun dpToPx(dp: Float): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
    private fun getCredentialsFile(): String {
        try {
            // Define the file name
            val fileName = "credentials.txt"
           // val un : TextView = findViewById(R.id.electionname)
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
                            return username!!
                            //un.setText("ELECTION NAME : $username")
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
        return ""
    }
}