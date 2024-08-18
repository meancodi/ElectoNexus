package com.example.electonexus_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class voter_sign_up : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voter_sign_up)
        val name: EditText = findViewById(R.id.LVoterName)
        val email: EditText = findViewById(R.id.LVoterEmailAddress)
        val password: EditText = findViewById(R.id.LVoterPassword)

        val signup : Button = findViewById(R.id.LVoterSignup)

        signup.setOnClickListener {
            Intent(this,MainActivity::class.java).also { startActivity(it) }
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}